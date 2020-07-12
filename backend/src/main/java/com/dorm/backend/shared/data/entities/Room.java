package com.dorm.backend.shared.data.entities;

import com.dorm.backend.shared.data.entities.address.Address;
import com.dorm.backend.shared.data.entities.base.BaseEntity;
import com.dorm.backend.shared.data.enums.EAmenity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Room extends BaseEntity {

    @ManyToOne
    private User owner;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(mappedBy = "ofRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Picture> pictures;

    //TODO resolve to more safe cascading type
    @ManyToMany(mappedBy = "possibleRooms", cascade = CascadeType.ALL)
    private Set<User> possibleRoommates;

    @OneToOne(mappedBy = "rentedRoom")
    private User rentee;

    @OneToMany(mappedBy = "room")
    private List<Message> chat;

    @Enumerated(value = EnumType.STRING)
    @ElementCollection(targetClass = EAmenity.class)
    private List<EAmenity> amenities;

    //fields

    private String name;

    private String description;

    private BigDecimal monthlyPrice;

    private BigDecimal deposit;

    private Date availableFrom;

    private Integer minDuration;

    private Integer houseArea;

    private Integer roomsNumber;
}

