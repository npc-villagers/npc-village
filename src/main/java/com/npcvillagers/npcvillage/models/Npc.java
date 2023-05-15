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
    private String name = "Any";
    private Species species;
    private String subspecies;
    private Gender gender;
    private Alignment alignment;

    // Fields to handle the custom option for age
    private AgeCategory ageCategory;
    private String customAge;
    private String age;
    @Column(columnDefinition = "text")
    private String voice = "Any";

    // Fields to handle the custom option for occupation
    private OccupationCategory occupationCategory;
    private String customOccupation;
    private String occupation;

    private CharacterClass characterClass;
    private CampaignStyle campaignStyle;
    private List<String> themes;
    private PlayerRelationship playerRelationship;
    @Column(columnDefinition = "text")
    private String appearance = "Any";
    @Column(columnDefinition = "text")
    private String personality = "Any";
    @Column(columnDefinition = "text")
    private String motivation = "Any";
    @Column(columnDefinition = "text")
    private String ideal = "Any";
    @Column(columnDefinition = "text")
    private String bond = "Any";
    @Column(columnDefinition = "text")
    private String flaw = "Any";
    @Column(columnDefinition = "text")
    private String history = "Any";
    @ManyToOne
    @JoinColumn(name = "appUser_id")
    private AppUser appUser;

    // Constructors
    public Npc() {
        // empty
    }

    public Npc(String name, Species species, String subspecies, Gender gender, Alignment alignment, AgeCategory ageCategory, String customAge, String voice, OccupationCategory occupationCategory, String customOccupation, CharacterClass characterClass, CampaignStyle campaignStyle, PlayerRelationship playerRelationship, String appearance, String personality, String motivation, String ideal, String bond, String flaw, String history) {
        this.name = name;
        setSpecies(species);
        setSubspecies(subspecies);
        setGender(gender);
        setAlignment(alignment);
        setAgeCategory(ageCategory);
        this.customAge = customAge;
        this.age = getAge();
        this.voice = voice;
        setOccupationCategory(occupationCategory);
        this.customOccupation = customOccupation;
        this.occupation = getOccupation();
        setCharacterClass(characterClass);
        setCampaignStyle(campaignStyle);
        setPlayerRelationship(playerRelationship);
        this.appearance = appearance;
        this.personality = personality;
        this.motivation = motivation;
        this.ideal = ideal;
        this.bond = bond;
        this.flaw = flaw;
        this.history = history;
    }

    // Getters and setters
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
        if (species.name().startsWith("ANY")) {
            this.species = species.getRandomSpecies();
        } else {
            this.species = species;
        }
    }

    public String getSubspecies() {
        return subspecies;
    }

    public void setSubspecies(String subspecies) {
        if ("Any".equals(subspecies)) {
            this.subspecies = species.getRandomSubspecies();
        } else {
            this.subspecies = subspecies;
        }
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        if (gender == Gender.ANY) {
            this.gender = gender.getRandomGender();
        } else {
            this.gender = gender;
        }
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(Alignment alignment) {
        if (alignment.isAnyType()) {
            this.alignment = alignment.getRandomAlignment();
        } else {
            this.alignment = alignment;
        }
    }

    public AgeCategory getAgeCategory() {
        return ageCategory;
    }

    public void setAgeCategory(AgeCategory ageCategory) {
        if (ageCategory.isAnyType()) {
            this.ageCategory = ageCategory.getRandomAgeCategory();
        } else {
            this.ageCategory = ageCategory;
        }
    }

    public String getCustomAge() {
        return customAge;
    }

    public void setCustomAge(String customAge) {
        this.customAge = customAge;
    }

    public String getAge() {
        if (this.ageCategory == AgeCategory.CUSTOM) {
            return this.customAge;
        } else {
            return this.ageCategory.getDisplayName();
        }
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
        if (occupationCategory == OccupationCategory.ANY) {
            this.occupationCategory = occupationCategory.getRandomOccupation();
        } else {
            this.occupationCategory = occupationCategory;
        }
    }

    public String getCustomOccupation() {
        return customOccupation;
    }

    public void setCustomOccupation(String customOccupation) {
        this.customOccupation = customOccupation;
    }

    public String getOccupation() {
        if (this.occupationCategory == OccupationCategory.CUSTOM) {
            return this.customOccupation;
        } else {
            return this.occupationCategory.getDisplayName();
        }
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(CharacterClass characterClass) {
        if (characterClass == characterClass.ANY) {
            this.characterClass = characterClass.getRandomClass();
        } else {
            this.characterClass = characterClass;
        }
    }

    public CampaignStyle getCampaignStyle() {
        return campaignStyle;
    }

    public void setCampaignStyle(CampaignStyle campaignStyle) {
        this.campaignStyle = campaignStyle;
        this.themes = campaignStyle.getThemes();
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
        if (playerRelationship == PlayerRelationship.ANY) {
            this.playerRelationship = playerRelationship.getRandomPlayerRelationship();
        } else {
            this.playerRelationship = playerRelationship;
        }
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
                '}';
    }
}
