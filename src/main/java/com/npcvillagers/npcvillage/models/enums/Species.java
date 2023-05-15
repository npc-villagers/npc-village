package com.npcvillagers.npcvillage.models.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

public enum Species {
    ANY("Any", Arrays.asList("Any"), SpeciesType.ANY),
    ANY_CORE("Any Core", Arrays.asList("Any"), SpeciesType.CORE),
    ANY_MONSTROUS("Any Monstrous", Arrays.asList("Any"), SpeciesType.MONSTROUS),
    ANY_EXOTIC("Any Exotic", Arrays.asList("Any"), SpeciesType.EXOTIC),
    ANY_EBERRON("Any Eberron", Arrays.asList("Any"), SpeciesType.EBERRON),
    ANY_RAVNICA("Any Ravnica", Arrays.asList("Any"), SpeciesType.RAVNICA),
    AARAKOCRA("Aarakocra", Arrays.asList("N/A"), SpeciesType.EXOTIC),
    AASIMAR("Aasimar", Arrays.asList("N/A"), SpeciesType.EXOTIC),
    BUGBEAR("Bugbear", Arrays.asList("N/A"), SpeciesType.MONSTROUS),
    CENTAUR("Centaur", Arrays.asList("N/A"), SpeciesType.MONSTROUS),
    CHANGELING("Changeling", Arrays.asList("N/A"), SpeciesType.EBERRON),
    DRAGONBORN("Dragonborn", Arrays.asList("Any", "Chromatic Dragonborn", "Gem Dragonborn", "Metallic Dragonborn"), SpeciesType.CORE),
    DWARF("Dwarf", Arrays.asList("Any", "Mountain Dwarf", "Hill Dwarf", "Duergar"), SpeciesType.CORE),
    ELADRIN("Eladrin", Arrays.asList("N/A"), SpeciesType.EXOTIC),
    ELF("Elf", Arrays.asList("Any", "High Elf", "Wood Elf", "Dark Elf", "Sea Elf", "Shadar-kai"), SpeciesType.CORE),
    FAIRY("Fairy", Arrays.asList("N/A"), SpeciesType.EXOTIC),
    FIRBOLG("Firbolg", Arrays.asList("N/A"), SpeciesType.EXOTIC),
    GENASI("Genasi", Arrays.asList("Any", "Air Genasi", "Earth Genasi", "Fire Genasi", "Water Genasi"), SpeciesType.EXOTIC),
    GITHYANKI("Githyanki", Arrays.asList("N/A"), SpeciesType.EXOTIC),
    GITHZERAI("Githzerai", Arrays.asList("N/A"), SpeciesType.EXOTIC),
    GNOME("Gnome", Arrays.asList("Any", "Rock Gnome", "Forest Gnome", "Deep Gnome"), SpeciesType.CORE),
    GOBLIN("Goblin", Arrays.asList("N/A"), SpeciesType.MONSTROUS),
    GOLIATH("Goliath", Arrays.asList("N/A"), SpeciesType.EXOTIC),
    HALF_ELF("Half-Elf", Arrays.asList("N/A"), SpeciesType.CORE),
    HALF_ORC("Half-Orc", Arrays.asList("N/A"), SpeciesType.CORE),
    HALFLING("Halfling", Arrays.asList("Any", "Lightfoot Halfling", "Stout Halfling", "Ghostwise Halfling"), SpeciesType.CORE),
    HARENGON("Harengon", Arrays.asList("N/A"), SpeciesType.EXOTIC),
    HOBGOBLIN("Hobgoblin", Arrays.asList("N/A"), SpeciesType.MONSTROUS),
    HUMAN("Human", Arrays.asList("N/A"), SpeciesType.CORE),
    KALASHTAR("Kalashtar", Arrays.asList("N/A"), SpeciesType.EBERRON),
    KENKU("Kenku", Arrays.asList("N/A"), SpeciesType.EXOTIC),
    KOBOLD("Kobold", Arrays.asList("N/A"), SpeciesType.MONSTROUS),
    LEONIN("Leonin", Arrays.asList("N/A"), SpeciesType.EXOTIC),
    LIZARDFOLK("Lizardfolk", Arrays.asList("N/A"), SpeciesType.MONSTROUS),
    LOXODON("Loxodon", Arrays.asList("N/A"), SpeciesType.RAVNICA),
    MINOTAUR("Minotaur", Arrays.asList("N/A"), SpeciesType.EXOTIC),
    ORC("Orc", Arrays.asList("N/A"), SpeciesType.MONSTROUS),
    OWLIN("Owlin", Arrays.asList("N/A"), SpeciesType.EXOTIC),
    SATYR("Satyr", Arrays.asList("N/A"), SpeciesType.EXOTIC),
    SHIFTER("Shifter", Arrays.asList("N/A"), SpeciesType.EBERRON),
    SIMIC_HYBRID("Simic Hybrid", Arrays.asList("N/A"), SpeciesType.RAVNICA),
    TABAXI("Tabaxi", Arrays.asList("N/A"), SpeciesType.EXOTIC),
    TIEFLING("Tiefling", Arrays.asList("N/A"), SpeciesType.CORE),
    TORTLE("Tortle", Arrays.asList("N/A"), SpeciesType.EXOTIC),
    TRITON("Triton", Arrays.asList("N/A"), SpeciesType.EXOTIC),
    VEDALKEN("Vedalken", Arrays.asList("N/A"), SpeciesType.RAVNICA),
    WARFORGED("Warforged", Arrays.asList("N/A"), SpeciesType.EBERRON),
    YUAN_TI("Yuan-ti", Arrays.asList("N/A"), SpeciesType.MONSTROUS);

    public enum SpeciesType {
        ANY,
        CORE,
        MONSTROUS,
        EXOTIC,
        EBERRON,
        RAVNICA
    }

    private final String displayName;
    private final List<String> subspecies;
    private final SpeciesType speciesType;

    Species(String displayName, List<String> subspecies, SpeciesType speciesType) {
        this.displayName = displayName;
        this.subspecies = subspecies;
        this.speciesType = speciesType;
    }

    public  Species getRandomSpecies() {
        // For the general "Any" case, return a random species from all available species
        if (this == ANY) {
            return getRandomAnySpecies();
        }
        // For all other "Any" cases, filter based on the provided SpeciesType and return a random species
        else if (this == ANY_CORE || this == ANY_MONSTROUS || this == ANY_EXOTIC || this == ANY_EBERRON || this == ANY_RAVNICA) {
            return getRandomFilteredSpecies(speciesType);
        }
        // Otherwise, just return the Species
        else {
            return this;
        }
    }

    public Species getRandomAnySpecies() {
        List<Species>  filteredSpecies = Arrays.stream(Species.values())
                .filter(species -> !species.name().startsWith("ANY"))
                .collect(toList());

        Random rand = new Random();

        return filteredSpecies.get(rand.nextInt(filteredSpecies.size()));
    }

    public static Species getRandomFilteredSpecies(SpeciesType speciesType) {
        List<Species>  filteredSpecies = Arrays.stream(Species.values())
                .filter(species -> species.getSpeciesType() == speciesType && !species.name().startsWith("ANY"))
                .collect(toList());

        Random rand = new Random();

        return filteredSpecies.get(rand.nextInt(filteredSpecies.size()));
    }

    public String getRandomSubspecies() {
        if (this.subspecies.size() == 1 && this.subspecies.get(0).equals("N/A")) {
            return "N/A";
        }

        List<String> subspeciesList = new ArrayList<>(this.subspecies);
        subspeciesList.remove("Any");
        Random rand = new Random();

        return subspeciesList.get(rand.nextInt(subspeciesList.size()));
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getSubspecies() {
        return subspecies;
    }

    public SpeciesType getSpeciesType() {
        return speciesType;
    }
}
