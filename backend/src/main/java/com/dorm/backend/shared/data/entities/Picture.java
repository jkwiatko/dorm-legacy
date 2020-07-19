package com.dorm.backend.shared.data.entities;

import com.dorm.backend.shared.data.entities.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class Picture extends BaseEntity {

    @ManyToOne
    private User owner;

    @ManyToOne
    private Room ofRoom;

    @ManyToOne
    private User ofUser;

    //fields

    private String url;

    @Column(unique = true)
    private String pictureName;

    private Integer pictureOrder;

}
