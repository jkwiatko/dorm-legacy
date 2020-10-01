package com.dorm.backend.shared.data.entity;

import com.dorm.backend.shared.data.entity.base.BaseEntity;
import com.dorm.backend.shared.data.entity.message.Chat;
import com.dorm.backend.shared.data.entity.picture.LocalPictureEntity;
import com.dorm.backend.shared.data.enums.Gender;
import com.dorm.backend.shared.data.enums.Inclination;
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
    private List<LocalPictureEntity> profilePictures;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Room> possibleRooms;

    @OneToMany(mappedBy = "owner")
    private List<Chat> ownedChats;

    @OneToMany(mappedBy = "mate")
    private List<Chat> matesChats;

    @ElementCollection
    private List<String> interests;

    @Enumerated(value = EnumType.STRING)
    @ElementCollection(targetClass = Inclination.class)
    private List<Inclination> inclinations;

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
    private Gender gender;

    private String workingIn;

    private String studyingAt;

    private String cleaningPolicy;

    private String smokingPolicy;

    private String petPolicy;

    private String guestsPolicy;
}
