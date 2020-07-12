package com.dorm.backend.shared.data.entities;

import com.dorm.backend.shared.data.entities.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
public class Message extends BaseEntity {

    @OneToOne
    User to;

    @OneToOne
    User from;

    @ManyToOne()
    Room room;

    //fields

    String message;
}
