package com.npcvillagers.npcvillage.services;

import com.google.gson.*;
import com.npcvillagers.npcvillage.models.Npc;
import com.npcvillagers.npcvillage.models.NpcForm;
import com.npcvillagers.npcvillage.models.enums.*;
import com.npcvillagers.npcvillage.utils.CustomEnumTypeAdapter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NpcFactory {

    public Npc createNpc(NpcForm form) {
        // Convert the NpcForm to an Npc and return it.
        Npc npc = new Npc();

        npc.setCreationMethod(form.getCreationMethod());
        // All the text entry fields are set depending on whether the user chooses Manual or AI Assistant methods. For the manual method, if the field is empty or is "Any", we set each to "Placeholder", otherwise we set to "Any" if it is empty for the AI Assistant to handle.

        if (npc.getCreationMethod() == CreationMethod.MANUAL) {
            if (form.getName().isEmpty() || "Any".equals(form.getName())) {
                npc.setName("Placeholder");
            } else {
                npc.setName(form.getName());
            }
            if (form.getVoice().isEmpty() || "Any".equals(form.getVoice())) {
                npc.setVoice("Placeholder");
            } else {
                npc.setVoice(form.getVoice());
            }
            if (form.getAppearance().isEmpty() || "Any".equals(form.getAppearance())) {
                npc.setAppearance("Placeholder");
            } else {
                npc.setAppearance(form.getAppearance());
            }
            if (form.getPersonality().isEmpty() || "Any".equals(form.getPersonality())) {
                npc.setPersonality("Placeholder");
            } else {
                npc.setPersonality(form.getPersonality());
            }
            if (form.getMotivation().isEmpty() || "Any".equals(form.getMotivation())) {
                npc.setMotivation("Placeholder");
            } else {
                npc.setMotivation(form.getMotivation());
            }
            if (form.getIdeal().isEmpty() || "Any".equals(form.getIdeal())) {
                npc.setIdeal("Placeholder");
            } else {
                npc.setIdeal(form.getIdeal());
            }
            if (form.getBond().isEmpty() || "Any".equals(form.getBond())) {
                npc.setBond("Placeholder");
            } else {
                npc.setBond(form.getBond());
            }
            if (form.getFlaw().isEmpty() || "Any".equals(form.getFlaw())) {
                npc.setFlaw("Placeholder");
            } else {
                npc.setFlaw(form.getFlaw());
            }
            if (form.getHistory().isEmpty() || "Any".equals(form.getHistory())) {
                npc.setHistory("Placeholder");
            } else {
                npc.setHistory(form.getHistory());
            }

            // The customOccupation and customAge categories might be empty or "Any" as well, but we handle those later to set the derived occupation and age values. So in this location we will update the form's values if they are empty
            form.setCustomAge(form.getCustomAge().isEmpty() ? "Placeholder" : form.getCustomAge());

            form.setCustomOccupation(form.getCustomOccupation().isEmpty() ? "Placeholder" : form.getCustomOccupation());

        } else {
            npc.setName(form.getName().isEmpty() ? "Any" : form.getName());
            npc.setVoice(form.getVoice().isEmpty() ? "Any" : form.getVoice());
            npc.setAppearance(form.getAppearance().isEmpty() ? "Any" : form.getAppearance());
            npc.setPersonality(form.getPersonality().isEmpty() ? "Any" : form.getPersonality());
            npc.setMotivation(form.getMotivation().isEmpty() ? "Any" : form.getMotivation());
            npc.setIdeal(form.getIdeal().isEmpty() ? "Any" : form.getIdeal());
            npc.setBond(form.getBond().isEmpty() ? "Any" : form.getBond());
            npc.setFlaw(form.getFlaw().isEmpty() ? "Any" : form.getFlaw());
            npc.setHistory(form.getHistory().isEmpty() ? "Any" : form.getHistory());

            // The customOccupation and customAge categories might be empty as well, but we handle those later to set the derived occupation and age values. So in this location we will update the form's values if they are empty
            form.setCustomAge(form.getCustomAge().isEmpty() ? "Any" : form.getCustomAge());

            form.setCustomOccupation(form.getCustomOccupation().isEmpty() ? "Any" : form.getCustomOccupation());
        }

        // If the form species starts with ANY, then get a random species, otherwise set the species to the form value
        Species formSpecies = form.getSpecies();
        npc.setSpecies(formSpecies.name().startsWith("ANY") ? formSpecies.getRandomSpecies() : formSpecies);

        // We do the same sort of thing for any dropdown field that has an "Any" option
        String formSubspecies = form.getSubspecies();
        npc.setSubspecies("Any".equals(formSubspecies) ? npc.getSpecies().getRandomSubspecies() : formSubspecies);
        
        Gender formGender = form.getGender();
        npc.setGender(formGender == Gender.ANY ? formGender.getRandomGender() : formGender);
        
        Alignment formAlignment = form.getAlignment();
        npc.setAlignment(formAlignment.isAnyType() ? formAlignment.getRandomAlignment() : formAlignment);

        AgeCategory formAgeCategory = form.getAgeCategory();
        npc.setAgeCategory(formAgeCategory.isAnyType() ? formAgeCategory.getRandomAgeCategory() : formAgeCategory);
        String formCustomAge = form.getCustomAge();
        npc.setCustomAge(formCustomAge);

        // The actual age field is dependent on whether the user has chosen the custom category and entered a custom age
        npc.setAge(formAgeCategory == AgeCategory.CUSTOM ? formCustomAge : npc.getAgeCategory().getDisplayName());

        OccupationCategory formOccupationCategory = form.getOccupationCategory();
        npc.setOccupationCategory(formOccupationCategory == OccupationCategory.ANY ? formOccupationCategory.getRandomOccupation() : formOccupationCategory);

        String formCustomOccupation = form.getCustomOccupation();
        npc.setCustomOccupation(formCustomOccupation);

        // The actual occupation field is dependent on whether the user has entered chosen the custom category and entered a custom occupation
        npc.setOccupation(formOccupationCategory == OccupationCategory.CUSTOM ? formCustomOccupation : npc.getOccupationCategory().getDisplayName());

        CharacterClass formCharacterClass = form.getCharacterClass();
        npc.setCharacterClass(formCharacterClass == CharacterClass.ANY ? formCharacterClass.getRandomClass() : formCharacterClass);

        CampaignStyle formCampaignStyle = form.getCampaignStyle();
        npc.setCampaignStyle(formCampaignStyle);
        npc.setThemes(formCampaignStyle.getThemes());

        PlayerRelationship formPlayerRelationship = form.getPlayerRelationship();
        npc.setPlayerRelationship(formPlayerRelationship == PlayerRelationship.ANY ? formPlayerRelationship.getRandomPlayerRelationship() : formPlayerRelationship);

        return npc;
    }

    public NpcForm createNpcForm(Npc npc) {
        // Convert the Npc to an NpcForm and return it
        NpcForm form = new NpcForm();

        form.setName(npc.getName());
        form.setSpecies(npc.getSpecies());
        form.setSubspecies(npc.getSubspecies());
        form.setGender(npc.getGender());
        form.setAlignment(npc.getAlignment());
        form.setAgeCategory(npc.getAgeCategory());
        form.setCustomAge(npc.getCustomAge());
        form.setVoice(npc.getVoice());
        form.setOccupationCategory(npc.getOccupationCategory());
        form.setCustomOccupation(npc.getCustomOccupation());
        form.setCharacterClass(npc.getCharacterClass());
        form.setCampaignStyle(npc.getCampaignStyle());
        form.setPlayerRelationship(npc.getPlayerRelationship());
        form.setAppearance(npc.getAppearance());
        form.setPersonality(npc.getPersonality());
        form.setMotivation(npc.getMotivation());
        form.setIdeal(npc.getIdeal());
        form.setBond(npc.getBond());
        form.setFlaw(npc.getFlaw());
        form.setHistory(npc.getHistory());
        form.setCreationMethod(npc.getCreationMethod());

        return form;
    }

    public Npc updateNpc(NpcForm form, Npc npc) {

        npc.setName(form.getName());
        npc.setSpecies(form.getSpecies());
        npc.setSubspecies(form.getSubspecies());
        npc.setGender(form.getGender());
        npc.setAlignment(form.getAlignment());
        npc.setAgeCategory(form.getAgeCategory());
        npc.setCustomAge(form.getCustomAge());
        npc.setVoice(form.getVoice());
        npc.setOccupationCategory(form.getOccupationCategory());
        npc.setCustomOccupation(form.getCustomOccupation());
        npc.setCharacterClass(form.getCharacterClass());
        npc.setCampaignStyle(form.getCampaignStyle());
        npc.setPlayerRelationship(form.getPlayerRelationship());
        npc.setAppearance(form.getAppearance());
        npc.setPersonality(form.getPersonality());
        npc.setMotivation(form.getMotivation());
        npc.setIdeal(form.getIdeal());
        npc.setBond(form.getBond());
        npc.setFlaw(form.getFlaw());
        npc.setHistory(form.getHistory());
        npc.setCreationMethod(form.getCreationMethod());

        return npc;
    }

    // This method takes in an NpcJson object and returns a string that's formatted like JSON for our message to ChatGPT
    public String toPrettyJsonString(Npc npc) {
        // Create the Gson object. setPrettyPrinting() enables pretty printing.
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        // Create a new NpcJson object from this Npc, and convert it to a JSON string
        String jsonString = gson.toJson(new NpcJson(npc));
        // Replace escaped quotes with actual quotes
        jsonString = jsonString.replace("\\\"", "\"");

        return jsonString;
    }

    /* The approach of this method is fairly brittle due to how we get information from ChatGPT. Basically, we want only certain fields generated from GPT to reduce response time, so we serialize the existing NPC to a JSON object, then serialize ChatGPT's response and merge the fields that are not nullish into our original Npc. We then deserialize the merged JSON into a java object.

    In addition, ChatGPT will not consistently return the enums in a way that gson can automatically parse them, i.e., with their name field. We use the displayName() method for each enum as a custom type adapter so that gson knows what to look for in our deserialization.
    */
    public Npc updateNpcFromContent(Npc npcToUpdate, String content) {
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(CampaignStyle.class, new CustomEnumTypeAdapter<>(CampaignStyle.class))
                    .registerTypeAdapter(Gender.class, new CustomEnumTypeAdapter<>(Gender.class))
                    .registerTypeAdapter(PlayerRelationship.class, new CustomEnumTypeAdapter<>(PlayerRelationship.class))
                    .registerTypeAdapter(Species.class, new CustomEnumTypeAdapter<>(Species.class))
                    .disableHtmlEscaping()
                    .create();

            // Convert existing NPC to a JSON object
            JsonObject existingJson = JsonParser.parseString(gson.toJson(npcToUpdate)).getAsJsonObject();

            // Parse the content to a JsonElement
            JsonElement newElement = JsonParser.parseString(content);

            JsonObject newJson;

            // Check whether the JsonElement is a JSON array or a JSON object. This is not guaranteed one way or the other from ChatGPT's responses.
            if (newElement.isJsonObject()) {
                newJson = newElement.getAsJsonObject();
            } else if (newElement.isJsonArray() && newElement.getAsJsonArray().size() > 0) {
                newJson = newElement.getAsJsonArray().get(0).getAsJsonObject();
            } else {
                throw new IllegalArgumentException("Failed to update NPC using ChatGPT: ChatGPT's response does not contain any valid NPCs");
            }

            // Merge the new JSON object into the existing one
            for (Map.Entry<String, JsonElement> entry : newJson.entrySet()) {
                if (!entry.getValue().isJsonNull()) {
                    existingJson.add(entry.getKey(), entry.getValue());
                }
            }

            // Deserialize the merged JSON object back into the NPC object
            npcToUpdate = gson.fromJson(existingJson, Npc.class);

        } catch (JsonSyntaxException e) {
            // Handle the exceptions here
            throw new IllegalArgumentException("Failed to update NPC using ChatGPT: " + e.getMessage());
        }

        return npcToUpdate;
    }

    // Define a temporary class in the factory that contains only the fields we want in our JSON formatted String given to ChatGPT as input
    protected class NpcJson {
        String name;
        String species;
        String subspecies;
        String gender;
        String alignment;
        String age;
        String voice;
        String occupation;
        String characterClass;
        String campaignStyle;
        List<String> themes;
        String playerRelationship;
        String appearance;
        String personality;
        String motivation;
        String ideal;
        String bond;
        String flaw;
        String history;

        NpcJson(Npc npc) {
            this.name = npc.getName();
            this.species = npc.getSpecies().getDisplayName();
            this.subspecies = npc.getSubspecies();
            this.gender = npc.getGender().getDisplayName();
            this.alignment = npc.getAlignment().getDisplayName();
            this.age = npc.getAge();
            this.voice = npc.getVoice();
            this.occupation = npc.getOccupation();
            this.characterClass = npc.getCharacterClass().getDisplayName();
            this.campaignStyle = npc.getCampaignStyle().getDisplayName();
            this.themes = npc.getThemes();
            this.playerRelationship = npc.getPlayerRelationship().getDisplayName();
            this.appearance = npc.getAppearance();
            this.personality = npc.getPersonality();
            this.motivation = npc.getMotivation();
            this.ideal = npc.getIdeal();
            this.bond = npc.getBond();
            this.flaw = npc.getFlaw();
            this.history = npc.getHistory();
        }
    }
}
