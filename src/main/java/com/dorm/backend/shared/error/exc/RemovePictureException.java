package com.dorm.backend.shared.error.exc;

public class RemovePictureException extends RuntimeException {
    public RemovePictureException() {
        super("Could not remove object due to unknown IO error");
    }
}
