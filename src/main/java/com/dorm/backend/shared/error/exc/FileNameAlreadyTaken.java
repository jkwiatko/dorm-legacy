package com.dorm.backend.shared.error.exc;

public class FileNameAlreadyTaken extends RuntimeException {
    public FileNameAlreadyTaken() {
        super("File name is already taken for this user");
    }
}
