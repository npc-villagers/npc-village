package com.npcvillagers.npcvillage.models;

import com.npcvillagers.npcvillage.models.enums.*;
import jakarta.persistence.*;

import java.util.List;

public class NpcForm {

    // Fields
    private String name = "Any";
    private Species species = Species.ANY;
    private String subspecies = "Any";
    private Gender gender = Gender.ANY;
    private Alignment alignment = Alignment.ANY;

    // Fields to handle the custom option for age
    private AgeCategory ageCategory = AgeCategory.ANY;
    private String customAge;
    private String age;
    private String voice = "Any";

    // Fields to handle the custom option for occupation
    private OccupationCategory occupationCategory = OccupationCategory.ANY;
    private String customOccupation;


    private CharacterClass characterClass = CharacterClass.ANY;

    private CampaignStyle campaignStyle = CampaignStyle.HIGH_FANTASY;

    private PlayerRelationship playerRelationship = PlayerRelationship.ANY;

    private String appearance = "Any";

    private String personality = "Any";

    private String motivation = "Any";

    private String ideal = "Any";

    private String bond = "Any";

    private String flaw = "Any";

    private String history = "Any";


    // Constructor
    public NpcForm() {
        // empty
    }

    // Getters and setters


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public String getSubspecies() {
        return subspecies;
    }

    public void setSubspecies(String subspecies) {
        this.subspecies = subspecies;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public AgeCategory getAgeCategory() {
        return ageCategory;
    }

    public void setAgeCategory(AgeCategory ageCategory) {
        this.ageCategory = ageCategory;
    }

    public String getCustomAge() {
        return customAge;
    }

    public void setCustomAge(String customAge) {
        this.customAge = customAge;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public OccupationCategory getOccupationCategory() {
        return occupationCategory;
    }

    public void setOccupationCategory(OccupationCategory occupationCategory) {
        this.occupationCategory = occupationCategory;
    }

    public String getCustomOccupation() {
        return customOccupation;
    }

    public void setCustomOccupation(String customOccupation) {
        this.customOccupation = customOccupation;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(CharacterClass characterClass) {
        this.characterClass = characterClass;
    }

    public CampaignStyle getCampaignStyle() {
        return campaignStyle;
    }

    public void setCampaignStyle(CampaignStyle campaignStyle) {
        this.campaignStyle = campaignStyle;
    }

    public PlayerRelationship getPlayerRelationship() {
        return playerRelationship;
    }

    public void setPlayerRelationship(PlayerRelationship playerRelationship) {
        this.playerRelationship = playerRelationship;
    }

    public String getAppearance() {
        return appearance;
    }

    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public String getIdeal() {
        return ideal;
    }

    public void setIdeal(String ideal) {
        this.ideal = ideal;
    }

    public String getBond() {
        return bond;
    }

    public void setBond(String bond) {
        this.bond = bond;
    }

    public String getFlaw() {
        return flaw;
    }

    public void setFlaw(String flaw) {
        this.flaw = flaw;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    @Override
    public String toString() {
        return "NpcForm{" +
                "name='" + name + '\'' +
                ", species=" + species +
                ", subspecies='" + subspecies + '\'' +
                ", gender=" + gender +
                ", alignment=" + alignment +
                ", ageCategory=" + ageCategory +
                ", customAge='" + customAge + '\'' +
                ", age='" + age + '\'' +
                ", voice='" + voice + '\'' +
                ", occupationCategory=" + occupationCategory +
                ", customOccupation='" + customOccupation + '\'' +
                ", characterClass=" + characterClass +
                ", campaignStyle=" + campaignStyle +
                ", playerRelationship=" + playerRelationship +
                ", appearance='" + appearance + '\'' +
                ", personality='" + personality + '\'' +
                ", motivation='" + motivation + '\'' +
                ", ideal='" + ideal + '\'' +
                ", bond='" + bond + '\'' +
                ", flaw='" + flaw + '\'' +
                ", history='" + history + '\'' +
                '}';
    }
}
