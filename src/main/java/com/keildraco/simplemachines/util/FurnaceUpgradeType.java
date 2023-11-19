package com.keildraco.simplemachines.util;

public enum FurnaceUpgradeType {
    UPGRADE_NONE("NONE"),
    UPGRADE_HEAT("HEAT"),
    UPGRADE_ERROR("ERROR")
    ;

    private final String type;

    FurnaceUpgradeType(final String text) {
        this.type = text;
    }

    @Override
    public String toString() {
        return type;
    }
}
