package com.npcvillagers.npcvillage.models;

import com.npcvillagers.npcvillage.models.enums.*;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Npc {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Species species;
    private String subspecies;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Alignment alignment;

    // Fields to handle the custom option for age
    @Enumerated(EnumType.STRING)
    private AgeCategory ageCategory;
    private String customAge;
    private String age;
    @Column(columnDefinition = "text")
    private String voice;

    // Fields to handle the custom option for occupation
    @Enumerated(EnumType.STRING)
    private OccupationCategory occupationCategory = OccupationCategory.ANY;
    private String customOccupation;
    private String occupation;

    @Enumerated(EnumType.STRING)
    private CharacterClass characterClass;
    @Enumerated(EnumType.STRING)
    private CampaignStyle campaignStyle;
    private List<String> themes;;
    @Enumerated(EnumType.STRING)
    private PlayerRelationship playerRelationship;
    @Column(columnDefinition = "text")
    private String appearance;
    @Column(columnDefinition = "text")
    private String personality;
    @Column(columnDefinition = "text")
    private String motivation;
    @Column(columnDefinition = "text")
    private String ideal;
    @Column(columnDefinition = "text")
    private String bond;
    @Column(columnDefinition = "text")
    private String flaw;
    @Column(columnDefinition = "text")
    private String history;
    @ManyToOne
    @JoinColumn(name = "appUser_id")
    private AppUser appUser;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "npc", orphanRemoval = true)
    private Task task;
    // Constructors
    public Npc() {
        // empty
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
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

    public List<String> getThemes() {
        return themes;
    }

    public void setThemes(List<String> themes) {
        this.themes = themes;
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

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "Npc{" +
                "id=" + id +
                ", name='" + name + '\'' +
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
                ", occupation='" + occupation + '\'' +
                ", characterClass=" + characterClass +
                ", campaignStyle=" + campaignStyle +
                ", themes=" + themes +
                ", playerRelationship=" + playerRelationship +
                ", appearance='" + appearance + '\'' +
                ", personality='" + personality + '\'' +
                ", motivation='" + motivation + '\'' +
                ", ideal='" + ideal + '\'' +
                ", bond='" + bond + '\'' +
                ", flaw='" + flaw + '\'' +
                ", history='" + history + '\'' +
                ", appUser=" + appUser +
                ", task=" + task +
                '}';
    }

}
