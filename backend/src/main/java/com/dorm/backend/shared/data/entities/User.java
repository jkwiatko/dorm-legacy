package com.dorm.backend.shared.data.entities;

import com.dorm.backend.shared.data.entities.base.BaseEntity;
import com.dorm.backend.shared.data.enums.EGender;
import com.dorm.backend.shared.data.enums.EUserCharacteristic;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class User extends BaseEntity {

    @OneToOne
    private Room rentedRoom;

    @OneToMany(mappedBy = "owner")
    private List<Room> ownedRooms;

    @OneToMany(mappedBy = "ofUser", cascade = CascadeType.ALL)
    private List<Picture> profilePictures;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Room> possibleRooms;

    @ElementCollection
    private List<String> interests;

    @Enumerated(value = EnumType.STRING)
    @ElementCollection(targetClass = EUserCharacteristic.class)
    private List<EUserCharacteristic> inclinations;

    //fields

    private String firstName;

    private String lastName;

    private Date birthDate;

    private String email;

    private String password;

    @Column(length = 1000)
    private String description;

    private boolean active;

    @Enumerated(value = EnumType.STRING)
    private EGender gender;

    private String workingIn;

    private String studyingAt;

    private String cleaningPolicy;

    private String smokingPolicy;

    private String petPolicy;

    private String guestsPolicy;
}
