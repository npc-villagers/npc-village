package com.npcvillagers.npcvillage;

import com.npcvillagers.npcvillage.models.enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.npcvillagers.npcvillage.models.Npc;
import com.npcvillagers.npcvillage.services.OpenAiApiHandler;
import com.npcvillagers.npcvillage.services.NpcFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OpenAiApiHandlerTest {

    @Autowired
    private NpcFactory npcFactory;

    @Autowired
    private OpenAiApiHandler openAiApiHandler;
    private Npc npc;

    @BeforeEach
    public void setup() {

        // setup a sample Npc for testing
        npc = new Npc();
        npc.setName("Test NPC");
        npc.setSpecies(Species.HUMAN);
        npc.setSubspecies("Test Subspecies");
        npc.setGender(Gender.MALE);
        npc.setAlignment(Alignment.CHAOTIC_GOOD);
        npc.setAgeCategory(AgeCategory.CUSTOM);
        npc.setCustomAge("25");
        npc.setAge("25");
        npc.setVoice("Baritone");
        npc.setOccupationCategory(OccupationCategory.CUSTOM);
        npc.setCustomOccupation("Blacksmith");
        npc.setOccupation("Blacksmith");
        npc.setCharacterClass(CharacterClass.FIGHTER);
        npc.setCampaignStyle(CampaignStyle.HIGH_FANTASY);
        npc.setThemes(CampaignStyle.HIGH_FANTASY.getThemes());
        npc.setPlayerRelationship(PlayerRelationship.ALLY);
        npc.setAppearance("Tall and muscular");
        npc.setPersonality("Brave and kind");
        npc.setMotivation("Protect the village");
        npc.setIdeal("Justice");
        npc.setBond("His family");
        npc.setFlaw("Fear of spiders");
        npc.setHistory("Born in a small village...");
        npc.setCreationMethod(CreationMethod.AI_ASSISTANT);
    }

    @Test
    public void testProcessNpc() {
        // Create mock dependencies
        NpcFactory mockNpcFactory = Mockito.mock(NpcFactory.class);

        // Create a test NPC
        Npc npc = new Npc();
        npc.setName("Test NPC");

        // The string representation of the NPC
        String npcJson = "{\"name\":\"Test NPC\"}";

        // Stub the toPrettyJsonString and updateNpcFromContent methods in mockNpcFactory to return specific values when called
        when(mockNpcFactory.toPrettyJsonString(npc)).thenReturn(npcJson);
        when(mockNpcFactory.updateNpcFromContent(any(Npc.class), any(String.class))).thenReturn(npc);

        // Inject mock dependencies into OpenAiApiHandler
        openAiApiHandler.setNpcFactory(mockNpcFactory);

        // Act
        Npc updatedNpc = openAiApiHandler.processNpc(npc);

        // Assert
        assertEquals("Test NPC", updatedNpc.getName());
    }

    @Test
    public void processNpc_WithContentPolicyViolation_ThrowsIllegalArgumentException() {

        // Create an NPC with content that violates the content policy
        npc.setName("Fuck fuck fuck fuck");

        // Assert that calling processNpc throws a RuntimeException
        assertThrows(RuntimeException.class, () -> openAiApiHandler.processNpc(npc));
    }

}
