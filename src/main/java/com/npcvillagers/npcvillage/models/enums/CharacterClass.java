package com.npcvillagers.npcvillage.models.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

public enum CharacterClass {
    ANY("Any"),
    NONE("None"),
    BARBARIAN("Barbarian"),
    BARD("Bard"),
    CLERIC("Cleric"),
    DRUID("Druid"),
    FIGHTER("Fighter"),
    MONK("Monk"),
    PALADIN("Paladin"),
    RANGER("Ranger"),
    ROGUE("Rogue"),
    SORCERER("Sorcerer"),
    WARLOCK("Warlock"),
    WIZARD("Wizard");

    private String displayName;

    CharacterClass(String displayName) {
        this.displayName = displayName;
    }

    public CharacterClass getRandomClass() {
        // For the general "Any" case, return a random class from all available classes
        if (this == ANY) {
            Random rand = new Random();
            List<CharacterClass> filteredClasses = Arrays.stream(CharacterClass.values())
                    .filter(characterClass -> characterClass != CharacterClass.ANY && characterClass != CharacterClass.NONE)
                    .collect(toList());
            return filteredClasses.get(rand.nextInt(filteredClasses.size()));
        }
        // Otherwise, just return the class or option
        else {
            return this;
        }
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
