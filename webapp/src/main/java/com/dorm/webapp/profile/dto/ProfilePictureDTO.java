package com.dorm.webapp.profile.dto;

public class ProfilePictureDTO {
    private String  base64String;
    private String  name;

    public String getBase64String() {
        return base64String;
    }

    public void setBase64String(String base64String) {
        this.base64String = base64String;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
