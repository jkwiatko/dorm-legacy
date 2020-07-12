package com.dorm.backend.profile.dto;

import com.dorm.backend.shared.data.dtos.PictureDTO;
import com.dorm.backend.shared.data.dtos.RoomPreviewDTO;

import java.util.Date;
import java.util.List;

public class ProfileDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private List<PictureDTO> profilePictures;
    private Date birthDate;
    private String description;
    private String gender;
    private String workingIn;
    private String studyingAt;
    private List<String> interests;
    private List<String> inclinations;
    private String cleaningPolicy;
    private String smokingPolicy;
    private String petPolicy;
    private String guestsPolicy;
    private List<RoomPreviewDTO> ownedRooms;

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

    public List<PictureDTO> getProfilePictures() {
        return profilePictures;
    }

    public void setProfilePictures(List<PictureDTO> profilePictures) {
        this.profilePictures = profilePictures;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
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

    public String getWorkingIn() {
        return workingIn;
    }

    public void setWorkingIn(String work) {
        this.workingIn = work;
    }

    public String getStudyingAt() {
        return studyingAt;
    }

    public void setStudyingAt(String studyingAt) {
        this.studyingAt = studyingAt;
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

    public List<RoomPreviewDTO> getOwnedRooms() {
        return ownedRooms;
    }

    public void setOwnedRooms(List<RoomPreviewDTO> ownedRooms) {
        this.ownedRooms = ownedRooms;
    }
}
