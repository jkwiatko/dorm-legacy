package com.dorm.backend.shared.error.exc;

public class NoSuchCityException extends RuntimeException {

    public NoSuchCityException(String cityName) {
        super(String.format("Niestety wybrane miasto '%s' nie jest jeszcze wspierane", cityName));
    }
}
