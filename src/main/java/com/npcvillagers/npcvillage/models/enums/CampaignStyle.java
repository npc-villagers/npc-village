package com.npcvillagers.npcvillage.models.enums;

import java.util.Arrays;
import java.util.List;

public enum CampaignStyle {
    HIGH_FANTASY("High Fantasy", Arrays.asList("magic", "wonder", "heroism", "adventure", "mythical creatures", "epic battles", "destiny")),
    SWORD_AND_SORCERY("Sword and Sorcery", Arrays.asList("action", "adventure", "heroism", "magic", "epic battles", "treasure hunting", "monsters")),
    URBAN_FANTASY("Urban Fantasy", Arrays.asList("mystery", "magic", "urban life", "action", "adventure", "technology", "power struggles")),
    EPIC("Epic", Arrays.asList("destiny", "epic battles", "heroism", "mythical creatures", "power struggles", "adventure", "world-changing events")),
    LOW_MAGIC("Low Magic", Arrays.asList("survival", "mystery", "danger", "intrigue", "low technology", "realism", "hardship")),
    GRIMDARK("Grimdark", Arrays.asList("darkness", "violence", "ambiguity", "depravity", "fear", "tragedy", "survival")),
    POLITICAL_INTRIGUE("Political Intrigue", Arrays.asList("intrigue", "politics", "diplomacy", "power struggles", "betrayal", "espionage", "deception")),
    MYTHIC("Mythic", Arrays.asList("mythology", "powerful beings", "magic", "destiny", "mythical creatures", "epic battles", "legends")),
    SUPERHERO("Superhero", Arrays.asList("extraordinaryAbilities", "heroism", "villains", "powers", "gadgets", "secret identities", "savingTheWorld")),
    HEIST("Heist", Arrays.asList("thievery", "planning", "execution", "risk", "danger", "infiltration", "treasureHunting")),
    PIRATES("Pirates", Arrays.asList("swashbuckling", "highSeas", "treasureHunting", "seaMonsters", "navalBattles", "pirates", "adventure")),
    SURVIVAL("Survival", Arrays.asList("hostileDangerousEnvironment", "wilderness", "desert", "dungeon", "scavenging", "crafting", "limitedResources")),
    GOTHIC_HORROR("Gothic Horror", Arrays.asList("haunted mansions", "vampires", "ghosts", "monsters", "dark secrets", "psychological horror", "tragedy")),
    CYBERPUNK("Cyberpunk", Arrays.asList("dystopian future", "advanced technology", "cybernetic enhancements", "hacking", "corporate espionage", "rebellion", "augmented reality")),
    ALTERNATE_HISTORY("Alternate History", Arrays.asList("different developments", "consequences", "what-if scenarios", "historical fiction", "technology", "society", "politics")),
    TIME_TRAVEL("Time Travel", Arrays.asList("exploringDifferentErasTimelines", "changingHistory", "paradoxes", "consequences", "scienceFiction")),
    WESTERN("Western", Arrays.asList("frontier life", "gunfights", "outlaws", "justice", "duels", "horseback riding", "saloons")),
    HORROR("Horror", Arrays.asList("fear", "suspense", "terror", "unknown", "supernatural", "horror", "survival")),
    STEAMPUNK("Steampunk", Arrays.asList("technology", "invention", "adventure", "mystery", "clockwork", "steam power"));

    private final String displayName;
    private final List<String> themes;

    CampaignStyle(String displayName, List<String> themes) {
        this.displayName = displayName;
        this.themes = themes;
    }

    public List<String> getThemes() {
        return themes;
    }

    public String getDisplayName() {
        return displayName;
    }

}
