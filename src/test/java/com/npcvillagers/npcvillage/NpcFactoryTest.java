package com.npcvillagers.npcvillage;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.npcvillagers.npcvillage.models.*;
import com.npcvillagers.npcvillage.models.enums.*;
import com.npcvillagers.npcvillage.services.NpcFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.lang.reflect.Type;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class NpcFactoryTest {

    private NpcFactory npcFactory;
    private NpcForm npcForm;
    private Npc npc;

    @BeforeEach
    void setUp() {
        npcFactory = new NpcFactory();

        // setup an example NpcForm for testing
        npcForm = new NpcForm();
        npcForm.setName("Test NPC");
        npcForm.setSpecies(Species.HUMAN);
        npcForm.setSubspecies("Test Subspecies");
        npcForm.setGender(Gender.MALE);
        npcForm.setAlignment(Alignment.CHAOTIC_GOOD);
        npcForm.setAgeCategory(AgeCategory.CUSTOM);
        npcForm.setCustomAge("25");
        npcForm.setVoice("Baritone");
        npcForm.setOccupationCategory(OccupationCategory.CUSTOM);
        npcForm.setCustomOccupation("Blacksmith");
        npcForm.setCharacterClass(CharacterClass.FIGHTER);
        npcForm.setCampaignStyle(CampaignStyle.HIGH_FANTASY);
        npcForm.setPlayerRelationship(PlayerRelationship.ALLY);
        npcForm.setAppearance("Tall and muscular");
        npcForm.setPersonality("Brave and kind");
        npcForm.setMotivation("Protect the village");
        npcForm.setIdeal("Justice");
        npcForm.setBond("His family");
        npcForm.setFlaw("Fear of spiders");
        npcForm.setHistory("Born in a small village...");
        npcForm.setCreationMethod(CreationMethod.AI_ASSISTANT);

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
    void createNpc() {
        Npc createdNpc = npcFactory.createNpc(npcForm);
        assertNotNull(createdNpc);
        assertEquals(npcForm.getName(), createdNpc.getName());
        assertEquals(npcForm.getName(), createdNpc.getName());
        assertEquals(npcForm.getSpecies(), createdNpc.getSpecies());
        assertEquals(npcForm.getSubspecies(), createdNpc.getSubspecies());
        assertEquals(npcForm.getGender(), createdNpc.getGender());
        assertEquals(npcForm.getAlignment(), createdNpc.getAlignment());
        assertEquals(npcForm.getAgeCategory(), createdNpc.getAgeCategory());
        assertEquals(npcForm.getCustomAge(), createdNpc.getCustomAge());
        assertEquals(npcForm.getVoice(), createdNpc.getVoice());
        assertEquals(npcForm.getOccupationCategory(), createdNpc.getOccupationCategory());
        assertEquals(npcForm.getCustomOccupation(), createdNpc.getCustomOccupation());
        assertEquals(npcForm.getCharacterClass(), createdNpc.getCharacterClass());
        assertEquals(npcForm.getCampaignStyle(), createdNpc.getCampaignStyle());
        assertEquals(npcForm.getPlayerRelationship(), createdNpc.getPlayerRelationship());
        assertEquals(npcForm.getAppearance(), createdNpc.getAppearance());
        assertEquals(npcForm.getPersonality(), createdNpc.getPersonality());
        assertEquals(npcForm.getMotivation(), createdNpc.getMotivation());
        assertEquals(npcForm.getIdeal(), createdNpc.getIdeal());
        assertEquals(npcForm.getBond(), createdNpc.getBond());
        assertEquals(npcForm.getFlaw(), createdNpc.getFlaw());
        assertEquals(npcForm.getHistory(), createdNpc.getHistory());
        assertEquals(npcForm.getCreationMethod(), createdNpc.getCreationMethod());
    }

    @Test
    void createNpcForm() {
        NpcForm createdNpcForm = npcFactory.createNpcForm(npc);
        assertNotNull(createdNpcForm);
        assertEquals(createdNpcForm.getName(), npc.getName());
        assertEquals(createdNpcForm.getSpecies(), npc.getSpecies());
        assertEquals(createdNpcForm.getSubspecies(), npc.getSubspecies());
        assertEquals(createdNpcForm.getGender(), npc.getGender());
        assertEquals(createdNpcForm.getAlignment(), npc.getAlignment());
        assertEquals(createdNpcForm.getAgeCategory(), npc.getAgeCategory());
        assertEquals(createdNpcForm.getCustomAge(), npc.getCustomAge());
        assertEquals(createdNpcForm.getVoice(), npc.getVoice());
        assertEquals(createdNpcForm.getOccupationCategory(), npc.getOccupationCategory());
        assertEquals(createdNpcForm.getCustomOccupation(), npc.getCustomOccupation());
        assertEquals(createdNpcForm.getCharacterClass(), npc.getCharacterClass());
        assertEquals(createdNpcForm.getCampaignStyle(), npc.getCampaignStyle());
        assertEquals(createdNpcForm.getPlayerRelationship(), npc.getPlayerRelationship());
        assertEquals(createdNpcForm.getAppearance(), npc.getAppearance());
        assertEquals(createdNpcForm.getPersonality(), npc.getPersonality());
        assertEquals(createdNpcForm.getMotivation(), npc.getMotivation());
        assertEquals(createdNpcForm.getIdeal(), npc.getIdeal());
        assertEquals(createdNpcForm.getBond(), npc.getBond());
        assertEquals(createdNpcForm.getFlaw(), npc.getFlaw());
        assertEquals(createdNpcForm.getHistory(), npc.getHistory());
        assertEquals(createdNpcForm.getCreationMethod(), npc.getCreationMethod());
    }

    @Test
    void updateNpc() {
        Npc npcToUpdate = new Npc();
        Npc createdNpc = npcFactory.createNpc(npcForm);
        Npc updatedNpc = npcFactory.updateNpc(npcForm, npcToUpdate);
        assertNotNull(updatedNpc);
        assertEquals(updatedNpc.getName(), createdNpc.getName());
        assertEquals(updatedNpc.getSpecies(), createdNpc.getSpecies());
        assertEquals(updatedNpc.getSubspecies(), createdNpc.getSubspecies());
        assertEquals(updatedNpc.getGender(), createdNpc.getGender());
        assertEquals(updatedNpc.getAlignment(), createdNpc.getAlignment());
        assertEquals(updatedNpc.getAgeCategory(), createdNpc.getAgeCategory());
        assertEquals(updatedNpc.getCustomAge(), createdNpc.getCustomAge());
        assertEquals(updatedNpc.getVoice(), createdNpc.getVoice());
        assertEquals(updatedNpc.getOccupationCategory(), createdNpc.getOccupationCategory());
        assertEquals(updatedNpc.getCustomOccupation(), createdNpc.getCustomOccupation());
        assertEquals(updatedNpc.getCharacterClass(), createdNpc.getCharacterClass());
        assertEquals(updatedNpc.getCampaignStyle(), createdNpc.getCampaignStyle());
        assertEquals(updatedNpc.getPlayerRelationship(), createdNpc.getPlayerRelationship());
        assertEquals(updatedNpc.getAppearance(), createdNpc.getAppearance());
        assertEquals(updatedNpc.getPersonality(), createdNpc.getPersonality());
        assertEquals(updatedNpc.getMotivation(), createdNpc.getMotivation());
        assertEquals(updatedNpc.getIdeal(), createdNpc.getIdeal());
        assertEquals(updatedNpc.getBond(), createdNpc.getBond());
        assertEquals(updatedNpc.getFlaw(), createdNpc.getFlaw());
        assertEquals(updatedNpc.getHistory(), createdNpc.getHistory());
        assertEquals(updatedNpc.getCreationMethod(), createdNpc.getCreationMethod());
    }

    @Test
    public void toPrettyJsonStringTest() {
        // Set up the Gson instance
        Gson gson = new Gson();

        // Use the toPrettyJsonString method
        String actualJson = npcFactory.toPrettyJsonString(npc);

        // Parse the actualJson string back into a Map
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> actualMap = gson.fromJson(actualJson, type);

        // Assert that the expected values match the actual values
        assertEquals("Test NPC", actualMap.get("name"));
        assertEquals("Human", actualMap.get("species"));
        assertEquals("Test Subspecies", actualMap.get("subspecies"));
        assertEquals("Male", actualMap.get("gender"));
        assertEquals("Chaotic Good", actualMap.get("alignment"));
        assertEquals("25", actualMap.get("age"));
        assertEquals("Baritone", actualMap.get("voice"));
        assertEquals("Blacksmith", actualMap.get("occupation"));
        assertEquals("Fighter", actualMap.get("characterClass"));
        assertEquals("High Fantasy", actualMap.get("campaignStyle"));
        List<String> expectedThemes = Arrays.asList("magic", "wonder", "heroism", "adventure", "mythical creatures", "epic battles", "destiny");
        assertEquals(expectedThemes, actualMap.get("themes"));
        assertEquals("Ally", actualMap.get("playerRelationship"));
        assertEquals("Tall and muscular", actualMap.get("appearance"));
        assertEquals("Brave and kind", actualMap.get("personality"));
        assertEquals("Protect the village", actualMap.get("motivation"));
        assertEquals("Justice", actualMap.get("ideal"));
        assertEquals("His family", actualMap.get("bond"));
        assertEquals("Fear of spiders", actualMap.get("flaw"));
        assertEquals("Born in a small village...", actualMap.get("history"));
    }

    @Test
    void updateNpcFromContent() {
        // Given
        Npc newNpc = npc;
        newNpc.setName("Test");
        newNpc.setAge("Test");
        newNpc.setVoice("Test");
        newNpc.setOccupation("Test");
        newNpc.setAppearance("Test");
        newNpc.setPersonality("Test");
        newNpc.setMotivation("Test");
        newNpc.setIdeal("Test");
        newNpc.setBond("Test");
        newNpc.setFlaw("Test");
        newNpc.setHistory("Test");
        
        String newNpcJsonString = npcFactory.toPrettyJsonString(newNpc);
        Npc updatedNpc = npcFactory.updateNpcFromContent(npc, newNpcJsonString);
        assertNotNull(updatedNpc);
        assertEquals(updatedNpc.getName(), newNpc.getName());
        assertEquals(updatedNpc.getAge(), newNpc.getAge());
        assertEquals(updatedNpc.getVoice(), newNpc.getVoice());
        assertEquals(updatedNpc.getOccupation(), newNpc.getOccupation());
        assertEquals(updatedNpc.getAppearance(), newNpc.getAppearance());
        assertEquals(updatedNpc.getPersonality(), newNpc.getPersonality());
        assertEquals(updatedNpc.getMotivation(), newNpc.getMotivation());
        assertEquals(updatedNpc.getIdeal(), newNpc.getIdeal());
        assertEquals(updatedNpc.getBond(), newNpc.getBond());
        assertEquals(updatedNpc.getFlaw(), newNpc.getFlaw());
        assertEquals(updatedNpc.getHistory(), newNpc.getHistory());
    }

}
