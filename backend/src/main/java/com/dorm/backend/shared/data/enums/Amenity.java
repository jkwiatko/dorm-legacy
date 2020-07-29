package com.dorm.backend.shared.data.enums;

public enum Amenity {

    //place
    LOCATION("Localizacja"),
    BALCONY("Balkon"),
    GARDEN("Ogród"),
    PATIO("Patio"),
    PETS_ALLOWED("Zwięrzenta"),
    SECURITY_ALARMS("Alarm"),
    ELEVATOR("Winda"),

    //furniture
    DISHWASHER("Zmywarka"),
    FURNITURE("Meble"),
    AIR_CONDITIONING("Klimatyzacja"),

    //storage
    STORAGE("Komórka"),
    GARAGE("Garaż"),
    PARKING("Parking");

    private final String readableText;

    Amenity(String readableText) {
        this.readableText = readableText;
    }

    public static Amenity getEnum(String readableText) {
        for (Amenity value : Amenity.values()) {
            if (value.readableText.equals(readableText)) {
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
