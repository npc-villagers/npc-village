package com.npcvillagers.npcvillage.models.enums;

import java.util.Random;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public enum Gender {
    ANY("Any"),
    MALE("Male"),
    FEMALE("Female"),
    NON_BINARY("Non-binary");

    private String displayName;

    Gender(String displayName) {
        this.displayName = displayName;
    }

    public Gender getRandomGender() {
        // For the general "Any" case, return a random gender from all available genders
        if (this == ANY) {
            Random rand = new Random();
            List<Gender> filteredGenders = Arrays.stream(Gender.values())
                    .filter(gender -> gender != Gender.ANY)
                    .collect(toList());

            return filteredGenders.get(rand.nextInt(filteredGenders.size()));
        } else {
            // Otherwise, just return the gender
            return this;
        }
    }

    public String getDisplayName() {
        return displayName;
    }
}
