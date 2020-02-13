package com.dorm.backend.shared.error.exc;

public class DuplicatedPictureException extends RuntimeException {
    public DuplicatedPictureException() {
        super("Sent pictures contain duplicate");
    }
}
