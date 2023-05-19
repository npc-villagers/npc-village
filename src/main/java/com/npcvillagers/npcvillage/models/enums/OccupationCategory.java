package com.npcvillagers.npcvillage.models.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

public enum OccupationCategory {
    ANY("Any"),
    CUSTOM("Custom"),
    ADVENTURER("Adventurer"),
    ALCHEMIST("Alchemist"),
    ANTHROPOLOGIST("Anthropologist"),
    ARCHAEOLOGIST("Archaeologist"),
    ATHLETE("Athlete"),
    BLACKSMITH("Blacksmith"),
    BREWER("Brewer"),
    CARPENTER("Carpenter"),
    CHARLATAN("Charlatan"),
    CITY_WATCH("City Watch / Investigator"),
    CLOISTERED_SCHOLAR("Cloistered Scholar"),
    COOK("Cook"),
    COURTIER("Courtier"),
    CRIMINAL("Criminal"),
    ENCHANTER("Enchanter"),
    ENTERTAINER("Entertainer"),
    FACTION_AGENT("Faction Agent"),
    FARMER("Farmer"),
    FAR_TRAVELER("Far Traveler"),
    FISHER("Fisher"),
    FOLK_HERO("Folk Hero"),
    GAMBLER("Gambler"),
    GLADIATOR("Gladiator"),
    GRAVEDIGGER("Gravedigger"),
    GUARD("Guard"),
    HEALER("Healer"),
    HERMIT("Hermit"),
    HUNTER("Hunter"),
    INNKEEPER("Innkeeper"),
    INHERITOR("Inheritor"),
    MERCHANT("Merchant"),
    MERCENARY("Mercenary"),
    MINER("Miner"),
    NOBLE("Noble"),
    PERFORMER("Performer"),
    PRIEST("Priest"),
    SAILOR("Sailor"),
    SCHOLAR("Scholar"),
    SHIPWRIGHT("Shipwright"),
    SCRIBE("Scribe"),
    STABLEHAND("Stablehand"),
    TAILOR("Tailor"),
    THIEF("Thief"),
    TINKERER("Tinkerer"),
    URCHIN("Urchin");

    private String displayName;

    OccupationCategory(String displayName) {
        this.displayName = displayName;
    }

    public OccupationCategory getRandomOccupation() {
        // For the general "Any" case, return a random occupation from all available occupations
        if (this == ANY) {
            Random rand = new Random();
            List<OccupationCategory> filteredOccupations = Arrays.stream(OccupationCategory.values())
                    .filter(occupation -> occupation != OccupationCategory.ANY && occupation != OccupationCategory.CUSTOM)
                    .collect(toList());
            return filteredOccupations.get(rand.nextInt(filteredOccupations.size()));
        }
        // Otherwise, just return the occupation
        else {
            return this;
        }
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
