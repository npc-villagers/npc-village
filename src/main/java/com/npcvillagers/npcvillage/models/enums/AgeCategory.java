package com.npcvillagers.npcvillage.models.enums;

import java.util.Random;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public enum AgeCategory {
    ANY("Any"),
    CUSTOM("Custom"),
    ANY_YOUTH("Any Youth"),
    ANY_ADULT("Any Adult"),
    CHILD("Child"),
    ADOLESCENT("Adolescent"),
    YOUNG_ADULT("Young Adult"),
    ADULT("Adult"),
    ELDER("Elder");

    private String displayName;

    AgeCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public AgeCategory getRandomAgeCategory() {
        switch (this) {
            case ANY:
                return getRandomNonAnyAgeCategory();
            case ANY_YOUTH:
                return getRandomYouthAgeCategory();
            case ANY_ADULT:
                return getRandomAdultAgeCategory();
            default:
                return this;
        }
    }

    private AgeCategory getRandomNonAnyAgeCategory() {
        // For the general "Any" case, return a random age category from all available age categories, removing the "CUSTOM" enum
        Random rand = new Random();
        List<AgeCategory> filteredAges = Arrays.stream(AgeCategory.values())
                .filter(ageCategory -> !ageCategory.isAnyType())
                .filter(ageCategory -> ageCategory != AgeCategory.CUSTOM)
                .collect(toList());

        return filteredAges.get(rand.nextInt(filteredAges.size()));
    }

    private AgeCategory getRandomYouthAgeCategory() {
        Random rand = new Random();
        AgeCategory[] youthAgeCategories = { CHILD, ADOLESCENT };

        return youthAgeCategories[rand.nextInt(youthAgeCategories.length)];
    }

    private AgeCategory getRandomAdultAgeCategory() {
        Random rand = new Random();
        AgeCategory[] adultAgeCategories = { YOUNG_ADULT, ADULT, ELDER };

        return adultAgeCategories[rand.nextInt(adultAgeCategories.length)];
    }

    public boolean isAnyType() {
        return this == ANY || this == ANY_YOUTH || this == ANY_ADULT;
    }
}
