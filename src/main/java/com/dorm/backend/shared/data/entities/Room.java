package com.dorm.backend.shared.data.entities;

import com.dorm.backend.shared.data.entities.base.BaseEntity;
import com.dorm.backend.shared.enums.EAmenities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Period;
import java.util.Date;
import java.util.List;

@Entity
public class Room extends BaseEntity {

    private User owner;
    private List<Booking> bookings;
    private Address address;
    private List<Picture> pictures;
    private List<RoomInvite> roomInvites;
    private List<ResidenceOffer> residenceOffers;

    private String name;
    private String description;
    private BigDecimal monthlyPrice;
    private BigDecimal deposit;
    private Date availableFrom;
    private Integer minDuration;
    private Integer houseArea;
    private Integer roomsNumber;
    private List<EAmenities> amenities;

    @ManyToOne
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @OneToMany(mappedBy = "room")
    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @OneToOne(cascade = CascadeType.ALL)
    public Address getAddress() {
        return address;
    }

    @OneToMany(mappedBy = "ofRoom")
    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @OneToMany(mappedBy = "room")
    public List<RoomInvite> getRoomInvites() {
        return roomInvites;
    }

    public void setRoomInvites(List<RoomInvite> roomInvites) {
        this.roomInvites = roomInvites;
    }

    @OneToMany(mappedBy = "room")
    public List<ResidenceOffer> getResidenceOffers() {
        return residenceOffers;
    }

    public void setResidenceOffers(List<ResidenceOffer> residenceOffers) {
        this.residenceOffers = residenceOffers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(BigDecimal monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public Date getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(Date availableFrom) {
        this.availableFrom = availableFrom;
    }

    public Integer getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(Integer minDuration) {
        this.minDuration = minDuration;
    }

    public Integer getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(Integer houseArea) {
        this.houseArea = houseArea;
    }

    public Integer getRoomsNumber() {
        return roomsNumber;
    }

    public void setRoomsNumber(Integer roomsNumber) {
        this.roomsNumber = roomsNumber;
    }

    @Enumerated(value = EnumType.STRING)
    @ElementCollection(targetClass = EAmenities.class)
    public List<EAmenities> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<EAmenities> amenities) {
        this.amenities = amenities;
    }
}
