package com.dorm.backend.shared.enums;

public enum EUserCharacteristic {
    EARLY_BIRD("Rannym ptaszkiem"),
    NIGHT_OWL("Nocnym markiem"),
    PARTY_PERSON("Towarzyski"),
    SILENT("Cichy"),
    HEALTHY("Zrowy"),
    VEGAN("Vege"),
    VEGETARIAN("Wege"),
    PET_PAL("Miłośnikiem zwierząt"),
    SHOPAHOLIC("Zakupoholikiem"),
    SLEEPYHEAD("Spiochem");

    private final String readableText;

    EUserCharacteristic(String readableText) {
        this.readableText = readableText;
    }

    public static EUserCharacteristic getEnum(String readableText) {
        for(EUserCharacteristic value : EUserCharacteristic.values()) {
            if(value.readableText.equals(readableText)) {
                return value;
            }
        }
        return null;
    }

    public String getReadableText() {
        return readableText;
    }

    @Override
    public String toString() {
        return this.readableText;
    }
}
