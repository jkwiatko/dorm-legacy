package com.dorm.webapp.data.entity;

import com.dorm.webapp.data.shared.Amenities;
import com.dorm.webapp.data.shared.Gender;
import com.dorm.webapp.data.shared.RoommatePreferences;

import javax.persistence.*;
import java.util.List;

@Entity
public class User extends BaseEntity {

    private List<Room> ownedRooms;
    private List<Room> pickedRooms;
    private List<Booking> bookings;
    private List<Message> receivedMessages;
    private List<Message> sendMessages;
    private List<Picture> pictures;
    private List<Picture> profilePictures;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String description;
    private boolean active;
    private Gender gender;
    private List<RoommatePreferences> roommatePreferences;

    @OneToMany(mappedBy = "owner")
    public List<Room> getOwnedRooms() {
        return ownedRooms;
    }

    public void setOwnedRooms(List<Room> ownedRooms) {
        this.ownedRooms = ownedRooms;
    }

    @ManyToMany(mappedBy = "volunteers")
    public List<Room> getPickedRooms() {
        return pickedRooms;
    }

    public void setPickedRooms(List<Room> pickedRooms) {
        this.pickedRooms = pickedRooms;
    }

    @OneToMany(mappedBy = "renter")
    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @ManyToMany(mappedBy = "addresses")
    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    @OneToMany(mappedBy = "author")
    public List<Message> getSendMessages() {
        return sendMessages;
    }

    public void setSendMessages(List<Message> sendMessages) {
        this.sendMessages = sendMessages;
    }

    @OneToMany(mappedBy = "owner")
    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    @OneToMany(mappedBy = "ofUser")
    public List<Picture> getProfilePictures() {
        return profilePictures;
    }

    public void setProfilePictures(List<Picture> profilePictures) {
        this.profilePictures = profilePictures;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Enumerated(value = EnumType.STRING)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @ElementCollection(targetClass = Amenities.class)
    @Enumerated(value = EnumType.STRING)
    public List<RoommatePreferences> getRoommatePreferences() {
        return roommatePreferences;
    }

    public void setRoommatePreferences(List<RoommatePreferences> roommatePreferences) {
        this.roommatePreferences = roommatePreferences;
    }
}
