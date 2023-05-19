package com.npcvillagers.npcvillage.models.enums;

public enum CreationMethod {
    MANUAL("Manual"),
    AI_ASSISTANT("AI Assistant");

    private String displayName;

    CreationMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

