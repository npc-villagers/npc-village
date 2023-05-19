package com.npcvillagers.npcvillage.models.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

public enum PlayerRelationship {
    ANY("Any"),
    ALLY("Ally"),
    ENEMY("Enemy"),
    NEUTRAL("Neutral"),
    FRIEND("Friend"),
    RIVAL("Rival"),
    MENTOR("Mentor"),
    PROTEGE("Protege"),
    FAMILY("Family"),
    ROMANTIC_PARTNER("Romantic Partner"),
    UNKNOWN("Unknown");

    private String displayName;

    PlayerRelationship(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public PlayerRelationship getRandomPlayerRelationship() {
        // For the general "Any" case, return a random player relationship from all available player relationships
        if (this == ANY) {
            Random rand = new Random();
            List<PlayerRelationship> filteredPlayerRelationships = Arrays.stream(PlayerRelationship.values())
                    .filter(playerRelationship -> playerRelationship != PlayerRelationship.ANY)
                    .collect(toList());
            return filteredPlayerRelationships.get(rand.nextInt(filteredPlayerRelationships.size()));
        }
        // Otherwise, just return the player relationship
        else {
            return this;
        }
    }
}
