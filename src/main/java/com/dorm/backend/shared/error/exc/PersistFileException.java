package com.dorm.backend.shared.error.exc;

public class PersistFileException extends RuntimeException {
    public PersistFileException() {
        super("Could not persist object due to unknown IO error");
    }
}
