package com.dorm.webapp.data.entity;

import com.dorm.webapp.data.enums.EGender;
import com.dorm.webapp.data.enums.ERoommatePreferences;
import com.dorm.webapp.data.shared.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class User extends BaseEntity {

    private List<Room> ownedRooms;
    private List<Booking> bookings;
    private List<Message> receivedMessages;
    private List<Message> sendMessages;
    private List<Picture> profilePictures;
    private List<ResidenceOffer> residenceOffers;
    private List<RoomInvite> roomInvites;

    private String firstName;
    private String lastName;
    private Date birthDate;
    private String email;
    private String password;
    private String description;
    private boolean active;
    private EGender gender;
    private List<ERoommatePreferences> roommatePreferences;

    @OneToMany(mappedBy = "owner")
    public List<Room> getOwnedRooms() {
        return ownedRooms;
    }

    public void setOwnedRooms(List<Room> ownedRooms) {
        this.ownedRooms = ownedRooms;
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

    @OneToMany(mappedBy = "ofUser", cascade = CascadeType.ALL)
    public List<Picture> getProfilePictures() {
        return profilePictures;
    }

    public void setProfilePictures(List<Picture> profilePictures) {
        this.profilePictures = profilePictures;
    }

    @OneToMany(mappedBy = "fromUser")
    public List<ResidenceOffer> getResidenceOffers() {
        return residenceOffers;
    }

    public void setResidenceOffers(List<ResidenceOffer> residenceOffers) {
        this.residenceOffers = residenceOffers;
    }

    @OneToMany(mappedBy = "toUser")
    public List<RoomInvite> getRoomInvites() {
        return roomInvites;
    }

    public void setRoomInvites(List<RoomInvite> roomInvites) {
        this.roomInvites = roomInvites;
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
    public EGender getGender() {
        return gender;
    }

    public void setGender(EGender gender) {
        this.gender = gender;
    }

    @ElementCollection(targetClass = ERoommatePreferences.class)
    @Enumerated(value = EnumType.STRING)
    public List<ERoommatePreferences> getRoommatePreferences() {
        return roommatePreferences;
    }

    public void setRoommatePreferences(List<ERoommatePreferences> roommatePreferences) {
        this.roommatePreferences = roommatePreferences;
    }
}
