package com.npcvillagers.npcvillage.models.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

public enum Alignment {
    ANY("Any"),
    ANY_GOOD("Any Good"),
    ANY_EVIL("Any Evil"),
    ANY_LAWFUL("Any Lawful"),
    ANY_NEUTRAL("Any Neutral"),
    ANY_CHAOTIC("Any Chaotic"),
    LAWFUL_GOOD("Lawful Good"),
    NEUTRAL_GOOD("Neutral Good"),
    CHAOTIC_GOOD("Chaotic Good"),
    LAWFUL_NEUTRAL("Lawful Neutral"),
    NEUTRAL("Neutral"),
    CHAOTIC_NEUTRAL("Chaotic Neutral"),
    LAWFUL_EVIL("Lawful Evil"),
    NEUTRAL_EVIL("Neutral Evil"),
    CHAOTIC_EVIL("Chaotic Evil");

    private final String displayName;

    Alignment(String displayName) {
        this.displayName = displayName;
    }

    public  Alignment getRandomAlignment() {
        switch (this) {
            case ANY:
                return getRandomNonAnyAlignment();
            case ANY_GOOD:
                return getRandomGoodAlignment();
            case ANY_EVIL:
                return getRandomEvilAlignment();
            case ANY_LAWFUL:
                return getRandomLawfulAlignment();
            case ANY_NEUTRAL:
                return getRandomNeutralAlignment();
            case ANY_CHAOTIC:
                return getRandomChaoticAlignment();
            default:
                return this;
        }
    }

    private Alignment getRandomNonAnyAlignment() {
        List<Alignment> nonAnyAlignments = Arrays.stream(values())
                .filter(alignment -> !alignment.isAnyType())
                .collect(toList());
        return nonAnyAlignments.get(new Random().nextInt(nonAnyAlignments.size()));
    }

    private Alignment getRandomGoodAlignment() {
        Alignment[] goodAlignments = { LAWFUL_GOOD, NEUTRAL_GOOD, CHAOTIC_GOOD };
        return goodAlignments[new Random().nextInt(goodAlignments.length)];
    }

    private Alignment getRandomEvilAlignment() {
        Alignment[] evilAlignments = { LAWFUL_EVIL, NEUTRAL_EVIL, CHAOTIC_EVIL };
        return evilAlignments[new Random().nextInt(evilAlignments.length)];
    }

    private Alignment getRandomLawfulAlignment() {
        Alignment[] lawfulAlignments = { LAWFUL_GOOD, LAWFUL_NEUTRAL, LAWFUL_EVIL };
        return lawfulAlignments[new Random().nextInt(lawfulAlignments.length)];
    }

    private Alignment getRandomNeutralAlignment() {
        Alignment[] neutralAlignments = { NEUTRAL_GOOD, NEUTRAL, NEUTRAL_EVIL };
        return neutralAlignments[new Random().nextInt(neutralAlignments.length)];
    }

    private Alignment getRandomChaoticAlignment() {
        Alignment[] chaoticAlignments = { CHAOTIC_GOOD, CHAOTIC_NEUTRAL, CHAOTIC_EVIL };
        return chaoticAlignments[new Random().nextInt(chaoticAlignments.length)];
    }

    public boolean isAnyType() {
        return this == ANY || this == ANY_GOOD || this == ANY_EVIL || this == ANY_LAWFUL ||
                this == ANY_NEUTRAL || this == ANY_CHAOTIC;
    }

    public String getDisplayName() {
        return displayName;
    }

}
