package com.dorm.webapp.data.entity;

import com.dorm.webapp.data.shared.Amenities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Period;
import java.util.List;

@Entity
public class Room extends BaseEntity {

    private User owner;
    private List<User> volunteers;
    private List<Booking> bookings;
    private Address address;
    private List<Picture> pictures;

    private String name;
    private String description;
    private BigDecimal monthlyPrice;
    private BigDecimal additionalCosts;
    private BigDecimal deposit;
    private Period minDuration;
    private List<Amenities> amenities;

    @ManyToOne
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @ManyToMany
    public List<User> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(List<User> volunteers) {
        this.volunteers = volunteers;
    }

    @OneToMany(mappedBy = "room")
    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @OneToOne
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

    public BigDecimal getAdditionalCosts() {
        return additionalCosts;
    }

    public void setAdditionalCosts(BigDecimal additionalCosts) {
        this.additionalCosts = additionalCosts;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public Period getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(Period minDuration) {
        this.minDuration = minDuration;
    }

    @ElementCollection(targetClass = Amenities.class)
    @Enumerated(value = EnumType.STRING)
    public List<Amenities> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<Amenities> amenities) {
        this.amenities = amenities;
    }
}
