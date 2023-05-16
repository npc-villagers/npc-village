package com.npcvillagers.npcvillage.services;

import com.npcvillagers.npcvillage.models.AppUser;
import com.npcvillagers.npcvillage.models.Npc;
import com.npcvillagers.npcvillage.models.NpcForm;
import com.npcvillagers.npcvillage.models.enums.*;
import org.springframework.stereotype.Service;

@Service
public class NpcFactory {

    // We  need two methods for Npc creation from an NpcForm, if the user either adding the Npc to their village or editing a saved NPC, then we need the AppUser as an argument. Otherwise, we pass in an NpcForm.
    public Npc createNpc(NpcForm form, AppUser appUser) {
        // Convert the NpcForm to an Npc and return it.
        Npc npc = new Npc();

        npc.setName(form.getName());

        // If the form species starts with ANY, then get a random species, otherwise set the species to the form value
        Species formSpecies = form.getSpecies();
        npc.setSpecies(formSpecies.name().startsWith("ANY") ? formSpecies.getRandomSpecies() : formSpecies);

        // We do the same sort of thing for any dropdown field that has an "Any" option
        String formSubspecies = form.getSubspecies();
        npc.setSubspecies("Any".equals(formSubspecies) ? formSpecies.getRandomSubspecies() : formSubspecies);

        Gender formGender = form.getGender();
        npc.setGender(formGender == Gender.ANY ? formGender.getRandomGender() : formGender);

        Alignment formAlignment = form.getAlignment();
        npc.setAlignment(formAlignment.isAnyType() ? formAlignment.getRandomAlignment() : formAlignment);

        AgeCategory formAgeCategory = form.getAgeCategory();
        npc.setAgeCategory(formAgeCategory.isAnyType() ? formAgeCategory.getRandomAgeCategory() : formAgeCategory);

        String formCustomAge = form.getCustomAge();
        npc.setCustomAge(formCustomAge);

        // The actual age field is dependent on whether the user has entered a custom age
        npc.setAge(formAgeCategory == AgeCategory.CUSTOM ? formCustomAge : formAgeCategory.getDisplayName());

        npc.setVoice(form.getVoice());

        OccupationCategory formOccupationCategory = form.getOccupationCategory();
        npc.setOccupationCategory(formOccupationCategory == OccupationCategory.ANY ? formOccupationCategory.getRandomOccupation() : formOccupationCategory);

        String formCustomOccupation = form.getCustomOccupation();
        npc.setCustomOccupation(formCustomOccupation);

        // The actual occupation field is dependent on whether the user has entered a custom occupation
        npc.setOccupation(formOccupationCategory == OccupationCategory.CUSTOM ? formCustomOccupation : formOccupationCategory.getDisplayName());

        CharacterClass formCharacterClass = form.getCharacterClass();
        npc.setCharacterClass(formCharacterClass == CharacterClass.ANY ? formCharacterClass.getRandomClass() : formCharacterClass);

        CampaignStyle formCampaignStyle = form.getCampaignStyle();
        npc.setCampaignStyle(formCampaignStyle);
        npc.setThemes(formCampaignStyle.getThemes());

        PlayerRelationship formPlayerRelationship = form.getPlayerRelationship();
        npc.setPlayerRelationship(formPlayerRelationship == PlayerRelationship.ANY ? formPlayerRelationship.getRandomPlayerRelationship() : formPlayerRelationship);

        npc.setAppearance(form.getAppearance());
        npc.setPersonality(form.getPersonality());
        npc.setMotivation(form.getMotivation());
        npc.setIdeal(form.getIdeal());
        npc.setBond(form.getBond());
        npc.setFlaw(form.getFlaw());
        npc.setHistory(form.getHistory());

        return npc;
    }

    public Npc createNpc(NpcForm form) {
        // Convert the NpcForm to an Npc and return it.
        Npc npc = new Npc();

        npc.setName(form.getName());
        
        // If the form species starts with ANY, then get a random species, otherwise set the species to the form value
        Species formSpecies = form.getSpecies();
        npc.setSpecies(formSpecies.name().startsWith("ANY") ? formSpecies.getRandomSpecies() : formSpecies);
        
        // We do the same sort of thing for any dropdown field that has an "Any" option
        String formSubspecies = form.getSubspecies();
        npc.setSubspecies("Any".equals(formSubspecies) ? formSpecies.getRandomSubspecies() : formSubspecies);
        
        Gender formGender = form.getGender();
        npc.setGender(formGender == Gender.ANY ? formGender.getRandomGender() : formGender);
        
        Alignment formAlignment = form.getAlignment();
        npc.setAlignment(formAlignment.isAnyType() ? formAlignment.getRandomAlignment() : formAlignment);

        AgeCategory formAgeCategory = form.getAgeCategory();
        npc.setAgeCategory(formAgeCategory.isAnyType() ? formAgeCategory.getRandomAgeCategory() : formAgeCategory);
        
        String formCustomAge = form.getCustomAge();
        npc.setCustomAge(formCustomAge);
        
        // The actual age field is dependent on whether the user has entered a custom age
        npc.setAge(formAgeCategory == AgeCategory.CUSTOM ? formCustomAge : formAgeCategory.getDisplayName());
        
        npc.setVoice(form.getVoice());

        OccupationCategory formOccupationCategory = form.getOccupationCategory();
        npc.setOccupationCategory(formOccupationCategory == OccupationCategory.ANY ? formOccupationCategory.getRandomOccupation() : formOccupationCategory);

        String formCustomOccupation = form.getCustomOccupation();
        npc.setCustomOccupation(formCustomOccupation);

        // The actual occupation field is dependent on whether the user has entered a custom occupation
        npc.setOccupation(formOccupationCategory == OccupationCategory.CUSTOM ? formCustomOccupation : formOccupationCategory.getDisplayName());

        CharacterClass formCharacterClass = form.getCharacterClass();
        npc.setCharacterClass(formCharacterClass == CharacterClass.ANY ? formCharacterClass.getRandomClass() : formCharacterClass);

        CampaignStyle formCampaignStyle = form.getCampaignStyle();
        npc.setCampaignStyle(formCampaignStyle);
        npc.setThemes(formCampaignStyle.getThemes());

        PlayerRelationship formPlayerRelationship = form.getPlayerRelationship();
        npc.setPlayerRelationship(formPlayerRelationship == PlayerRelationship.ANY ? formPlayerRelationship.getRandomPlayerRelationship() : formPlayerRelationship);

        npc.setAppearance(form.getAppearance());
        npc.setPersonality(form.getPersonality());
        npc.setMotivation(form.getMotivation());
        npc.setIdeal(form.getIdeal());
        npc.setBond(form.getBond());
        npc.setFlaw(form.getFlaw());
        npc.setHistory(form.getHistory());

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

        return form;
    }
}
