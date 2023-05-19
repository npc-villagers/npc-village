package com.npcvillagers.npcvillage.services;

import com.google.gson.*;
import com.microsoft.azure.cognitiveservices.vision.contentmoderator.*;
import com.microsoft.azure.cognitiveservices.vision.contentmoderator.models.*;
import com.npcvillagers.npcvillage.models.Npc;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.moderation.Moderation;
import com.theokanning.openai.moderation.ModerationRequest;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAiApiHandler {

    @Autowired
    NpcFactory npcFactory;

    public OpenAiApiHandler(NpcFactory npcFactory) {
        this.npcFactory = npcFactory;
    }

    private static final String SEED_MESSAGE_SYSTEM = "You are a dungeon master's assistant for their creation of dungeons and dragons NPCs.";
    private static final String SEED_MESSAGE_USER_PATH = "src/main/resources/static/json/seedUserMessage.json";
    private static final String SEED_MESSAGE_USER = getSeedUserMessage();
    private static final String SEED_MESSAGE_ASSISTANT_PATH = "src/main/resources/static/json/seedAssistantMessage.json";
    private static final String SEED_MESSAGE_ASSISTANT = getSeedAssistantMessage();

    public Npc processNpc(Npc npc) {
        String userNpcJsonString = npcFactory.toPrettyJsonString(npc);

        long startTime = System.nanoTime();

        // Ensure that the user's inputs comply with OpenAI content policies
        enforceContentPolicy(userNpcJsonString);

        // Call the OpenAI API and create a JSON format string
        String generatedNpcJsonString = generateOpenAiChatMessage(userNpcJsonString);

        Npc updatedNpc = npcFactory.updateNpcFromContent(npc, generatedNpcJsonString);

        long endTime = System.nanoTime();

        // Calculate the elapsed time in seconds
        double elapsedTime = (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Elapsed time: " + elapsedTime + " seconds");

        return updatedNpc;
    }

    // We use the OpenAI moderations endpoint and the Azure Content Moderator to ensure what the user typed doesn't break content policy. Based on testing, the OpenAI moderations endpoint is not sufficient on its own in certain edge cases. We use it in concert with the Azure Moderator Client.
    private void enforceContentPolicy(String userNpcJsonString) {
        enforceOpenAiContentPolicy(userNpcJsonString);
        enforceAzureContentPolicy(userNpcJsonString);
    }

    private void enforceOpenAiContentPolicy(String userNpcJsonString) {
        String token = System.getenv("OPENAI_TOKEN");
        if (token == null) {
            throw new RuntimeException("Error: OPENAI_TOKEN environment variable not set");
        }

        OpenAiService service = null;

        try {
            // Set duration to 60 seconds to avoid a socket exception for long response times
            service = new OpenAiService(token, Duration.ofSeconds(60));

            ModerationRequest moderationRequest = ModerationRequest.builder()
                    .input(userNpcJsonString)
                    .model("text-moderation-latest")
                    .build();

            List<Moderation> moderationResults = service.createModeration(moderationRequest).getResults();

            // Check if any results were returned
            if (moderationResults.isEmpty()) {
                throw new RuntimeException("Error: No moderation results returned");
            }

            Moderation moderationScore = moderationResults.get(0);

            // Check if the content is flagged by OpenAI
            if (moderationScore.isFlagged()) {
                // Throw an exception indicating content policy violation
                throw new IllegalArgumentException("Content violates the content policy. Please modify your NPC");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error enforcing OpenAI content policy", e);
        } finally {
            if (service != null) {
                service.shutdownExecutor();
            }
        }
    }

    private void enforceAzureContentPolicy(String userNpcJsonString) {
        String azureModeratorEndpoint = System.getenv("AZURE_MODERATOR_ENDPOINT");
        if (azureModeratorEndpoint == null) {
            throw new RuntimeException("Error: AZURE_MODERATOR_ENDPOINT environment variable not set");
        }

        String azureModeratorSubscriptionKey = System.getenv("AZURE_MODERATOR_SUBSCRIPTION_KEY");
        if (azureModeratorEndpoint == null) {
            throw new RuntimeException("Error: AZURE_MODERATOR_SUBSCRIPTION_KEY environment variable not set");
        }

        try {
            // Create Azure Content Moderator client
            ContentModeratorClient azureModeratorClient = ContentModeratorManager.authenticate(
                    AzureRegionBaseUrl.fromString(azureModeratorEndpoint), azureModeratorSubscriptionKey);

            // Detect the language of the text
            DetectedLanguage detectedLanguage = azureModeratorClient.textModerations().detectLanguage("text/plain", userNpcJsonString.getBytes());

            if (detectedLanguage == null) {
                throw new RuntimeException("Failed to detect the language of the text");
            }

            // Screen the text
            ScreenTextOptionalParameter screenTextOptionalParameter = new ScreenTextOptionalParameter().withLanguage(detectedLanguage.detectedLanguage());
            Screen screen = azureModeratorClient.textModerations().screenText("text/plain", userNpcJsonString.getBytes(), screenTextOptionalParameter);

            // If there are any matched items in the Auto-detected language, PII or Classification categories.
            if ((screen.pII() != null) ||
                    (screen.classification() != null &&
                            (screen.classification().reviewRecommended()))) {
                throw new IllegalArgumentException("Content violates the content policy. Please modify your NPC");
            }
        } catch (APIErrorException e) {
            // Handle API exceptions here
            throw new RuntimeException("An error occurred while screening the content with Azure Content Moderator", e);
        } catch (Exception e) {
            // Handle other exceptions here
            throw new RuntimeException("An unexpected error occurred", e);
        }
    }

    private String generateOpenAiChatMessage(String userNpcJsonString) {
        String token = System.getenv("OPENAI_TOKEN");
        if (token == null) {
            throw new RuntimeException("Error: OPENAI_TOKEN environment variable not set");
        }

        OpenAiService service = null;

        try {
            // Set duration to 60 seconds to avoid a socket exception for long response times
            service = new OpenAiService(token, Duration.ofSeconds(60));

            // Seed the chat with system context, example input, and example output, and add the user's Npc to the messages
            final List<ChatMessage> messages = new ArrayList<>();
            final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), SEED_MESSAGE_SYSTEM);
            final ChatMessage seedUserMessage = new ChatMessage(ChatMessageRole.USER.value(), SEED_MESSAGE_USER);
            final ChatMessage seedAssistantMessage = new ChatMessage(ChatMessageRole.ASSISTANT.value(), SEED_MESSAGE_ASSISTANT);
            final ChatMessage userNpcRequestMessage = new ChatMessage(ChatMessageRole.USER.value(), userNpcJsonString);
            messages.add(systemMessage);
            messages.add(seedUserMessage);
            messages.add(seedAssistantMessage);
            messages.add(userNpcRequestMessage);

            // Send the API request
            ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                    .builder()
                    .model("gpt-3.5-turbo")
                    .messages(messages)
                    .n(1)
                    .temperature(0.8)
                    .maxTokens(1000)
                    .logitBias(new HashMap<>())
                    .build();

            // Extract the message content of the response
            List<ChatCompletionChoice> choices = service.createChatCompletion(chatCompletionRequest).getChoices();

            if (choices.isEmpty()) {
                throw new RuntimeException("Error: No response from OpenAI");
            }
            String content = choices.get(0).getMessage().getContent();

            return content;
        } catch (Exception e) {
            throw new RuntimeException("Error generating OpenAI chat message", e);
        } finally {
            if (service != null) {
                service.shutdownExecutor();
            }
        }
    }

    private static String getSeedUserMessage() {
        String prettyJsonString = getPrettyJsonString(SEED_MESSAGE_USER_PATH);
        String originalMessage = "Generate a JSON-formatted NPC (Non-Player Character) for use in a Dungeons and Dragons campaign. The JSON should ONLY contain the following fields: \"name\", \"age\", \"voice\", \"description\", \"personality\", \"motivation\", \"ideal\", \"bond\", \"flaw\", and \"history\". Please ensure that the fields \"ideal\", \"bond\", and \"flaw\" are described from a first-person point of view. IMPORTANT: Do not include any other fields beyond the ones specifically mentioned here, such as species, subspecies, gender, alignment or any other. IMPORTANT: The NPC's \"history\" or \"motivation\" should clearly reflect the given \"playerRelationship\" input, which describes the relationship of the NPC to the players. This relationship should be clearly visible in the NPC's backstory or motivation. IMPORTANT: Do not mis-gender your creation. IMPORTANT: Give the NPC's \"age\" in years.\n\n";
        String fullMessage = originalMessage + prettyJsonString;

        return fullMessage;
    }

    private static String getSeedAssistantMessage() {
        return getPrettyJsonString(SEED_MESSAGE_ASSISTANT_PATH);
    }

    private static String getPrettyJsonString(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            // create a Gson instance with pretty printing
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            // parse the JSON file into a JsonArray
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

            // convert the JsonArray into a pretty printed string
            String prettyJsonString = gson.toJson(jsonArray);

            // Replace escaped quotes with actual quotes
            prettyJsonString = prettyJsonString.replace("\\\"", "\"");

            return prettyJsonString;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public NpcFactory getNpcFactory() {
        return npcFactory;
    }

    public void setNpcFactory(NpcFactory npcFactory) {
        this.npcFactory = npcFactory;
    }
}
