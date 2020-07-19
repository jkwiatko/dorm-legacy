package com.dorm.backend.shared.data.entity.picture;

import com.dorm.backend.shared.data.entity.Room;
import com.dorm.backend.shared.data.entity.User;
import com.dorm.backend.shared.data.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "local_picture")
public class LocalPictureEntity extends BaseEntity {

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
