package com.dorm.backend.shared.data.dtos;

import com.dorm.backend.shared.data.entities.Picture;
import com.dorm.backend.shared.data.enums.EGender;

import java.util.Date;

public class ProfilePreviewDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private EGender gender;
    private Picture profilePictures;
    private String studyingAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public EGender getGender() {
        return gender;
    }

    public void setGender(EGender gender) {
        this.gender = gender;
    }

    public Picture getProfilePictures() {
        return profilePictures;
    }

    public void setProfilePictures(Picture profilePictures) {
        this.profilePictures = profilePictures;
    }

    public String getStudyingAt() {
        return studyingAt;
    }

    public void setStudyingAt(String studyingAt) {
        this.studyingAt = studyingAt;
    }
}
