package com.dorm.backend.shared.enums;

public enum EAmenity {
    //place
    LOCATION("Localizacja"),
    BALCONY("Blacon"),
    GARDEN("Ogród"),
    PATIO("Patio"),
    POOL("Basen"),
    PETS_ALLOWED("Zwięrzeta dozwolone"),
    SECURITY_ALARMS("Alarm antywłamaniowy"),
    ELEVATOR("Winda"),
    //furniture
    DISHWASHER("Zmywarka"),
    FURNITURE("Meble"),
    AIR_CONDITIONING("Klimatyzacja"),
    //storage
    STORAGE("Komórka"),
    GARAGE("Garaż"),
    PARKING("Miejsce Parkingowe");

    private final String readableText;

    EAmenity(String readableText) {
        this.readableText = readableText;
    }

    public static EAmenity getEnum(String readableText) {
        for(EAmenity value : EAmenity.values()) {
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
