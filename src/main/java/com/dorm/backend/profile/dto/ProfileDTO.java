package com.dorm.backend.profile.dto;

import java.util.List;

public class ProfileDTO {
    private String firstName;
    private String lastName;
    private ProfilePictureDTO profilePicture;
    private String birthDate;
    private String description;
    private String gender;
    private String work;
    private String university;
    private List<String> interests;
    private List<String> inclinations;
    private String cleaningPolicy;
    private String smokingPolicy;
    private String petPolicy;
    private String guestsPolicy;

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

    public ProfilePictureDTO getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(ProfilePictureDTO profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public List<String> getInclinations() {
        return inclinations;
    }

    public void setInclinations(List<String> inclinations) {
        this.inclinations = inclinations;
    }

    public String getCleaningPolicy() {
        return cleaningPolicy;
    }

    public void setCleaningPolicy(String cleaningPolicy) {
        this.cleaningPolicy = cleaningPolicy;
    }

    public String getSmokingPolicy() {
        return smokingPolicy;
    }

    public void setSmokingPolicy(String smokingPolicy) {
        this.smokingPolicy = smokingPolicy;
    }

    public String getPetPolicy() {
        return petPolicy;
    }

    public void setPetPolicy(String petPolicy) {
        this.petPolicy = petPolicy;
    }

    public String getGuestsPolicy() {
        return guestsPolicy;
    }

    public void setGuestsPolicy(String guestsPolicy) {
        this.guestsPolicy = guestsPolicy;
    }
}
