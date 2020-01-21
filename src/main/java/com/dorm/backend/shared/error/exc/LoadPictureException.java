package com.dorm.backend.shared.error.exc;

public class LoadPictureException extends RuntimeException {
    public LoadPictureException() {
        super("Could not load object due to unknown IO error");
    }
}
