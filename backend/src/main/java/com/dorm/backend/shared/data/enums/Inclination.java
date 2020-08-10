package com.dorm.backend.shared.data.enums;

public enum Inclination {
    EARLY_BIRD("Rannym ptaszkiem"),
    NIGHT_OWL("Nocnym markiem"),
    PARTY_PERSON("Towarzyski/a"),
    SILENT("Cichy/a"),
    HEALTHY("Fit"),
    VEGAN("Vege"),
    VEGETARIAN("Wege"),
    PET_PAL("Miłośnikiem/czką zwierząt"),
    SHOPAHOLIC("Zakupoholikiem/czką"),
    SLEEPYHEAD("Spiochem");

    private final String readableText;

    Inclination(String readableText) {
        this.readableText = readableText;
    }

    public static Inclination getEnum(String readableText) {
        for(Inclination value : Inclination.values()) {
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
