//package com.npcvillagers.npcvillage.services;
//
//import com.npcvillagers.npcvillage.models.*;
//import com.npcvillagers.npcvillage.models.enums.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//class NpcFactoryTest {
//
//    private NpcFactory npcFactory;
//    private NpcForm npcForm;
//    private Npc npc;
//
//    @BeforeEach
//    void setUp() {
//        npcFactory = new NpcFactory();
//
//        // setup an example NpcForm for testing
//        npcForm = new NpcForm();
//        npcForm.setName("Test NPC");
//        npcForm.setSpecies(Species.HUMAN);
//        npcForm.setSubspecies("Test Subspecies");
//        npcForm.setGender(Gender.MALE);
//        npcForm.setAlignment(Alignment.CHAOTIC_GOOD);
//        npcForm.setAgeCategory(AgeCategory.CUSTOM);
//        npcForm.setCustomAge("25");
//        npcForm.setVoice("Baritone");
//        npcForm.setOccupationCategory(OccupationCategory.CUSTOM);
//        npcForm.setCustomOccupation("Blacksmith");
//        npcForm.setCharacterClass(CharacterClass.FIGHTER);
//        npcForm.setCampaignStyle(CampaignStyle.HIGH_FANTASY);
//        npcForm.setPlayerRelationship(PlayerRelationship.ALLY);
//        npcForm.setAppearance("Tall and muscular");
//        npcForm.setPersonality("Brave and kind");
//        npcForm.setMotivation("Protect the village");
//        npcForm.setIdeal("Justice");
//        npcForm.setBond("His family");
//        npcForm.setFlaw("Fear of spiders");
//        npcForm.setHistory("Born in a small village...");
//        npcForm.setCreationMethod(CreationMethod.AI_ASSISTANT);
//
//        // setup a sample Npc for testing
//        npc = new Npc();
//        npc.setName("Test NPC");
//        npc.setSpecies(Species.HUMAN);
//        npc.setSubspecies("Test Subspecies");
//        npc.setGender(Gender.MALE);
//        npc.setAlignment(Alignment.CHAOTIC_GOOD);
//        npc.setAgeCategory(AgeCategory.CUSTOM);
//        npc.setCustomAge("25");
//        npc.setVoice("Baritone");
//        npc.setOccupationCategory(OccupationCategory.CUSTOM);
//        npc.setCustomOccupation("Blacksmith");
//        npc.setCharacterClass(CharacterClass.FIGHTER);
//        npc.setCampaignStyle(CampaignStyle.HIGH_FANTASY);
//        npc.setPlayerRelationship(PlayerRelationship.ALLY);
//        npc.setAppearance("Tall and muscular");
//        npc.setPersonality("Brave and kind");
//        npc.setMotivation("Protect the village");
//        npc.setIdeal("Justice");
//        npc.setBond("His family");
//        npc.setFlaw("Fear of spiders");
//        npc.setHistory("Born in a small village...");
//        npc.setCreationMethod(CreationMethod.AI_ASSISTANT);
//    }
//
//    @Test
//    void createNpc() {
//        Npc createdNpc = npcFactory.createNpc(npcForm);
//        assertNotNull(createdNpc);
//        assertEquals(npcForm.getName(), createdNpc.getName());
//    }
//
//    @Test
//    void createNpcForm() {
//        NpcForm createdNpcForm = npcFactory.createNpcForm(npc);
//        assertNotNull(createdNpcForm);
//        assertEquals(npc.getCreationMethod(), createdNpcForm.getCreationMethod());
//    }
//
//    @Test
//    void updateNpc() {
//        NpcForm newNpcForm = Mockito.mock(NpcForm.class);
//        Mockito.when(newNpcForm.getName()).thenReturn("Updated Name");
//        Npc updatedNpc = npcFactory.updateNpc(newNpcForm, npc);
//        assertNotNull(updatedNpc);
//        assertEquals(newNpcForm.getName(), updatedNpc.getName());
//    }
//
//    @Test
//    void toPrettyJsonString() {
//        String jsonString = npcFactory.toPrettyJsonString(npc);
//        assertNotNull(jsonString);
//        // Here you might want to verify the exact JSON output, depending on your requirements
//    }
//
//    @Test
//    void updateNpcFromContent() {
//        String content = "{...}"; // should be a valid content string that GPT might return
//        Npc updatedNpc = npcFactory.updateNpcFromContent(npc, content);
//        assertNotNull(updatedNpc);
//        // Assertions
//
