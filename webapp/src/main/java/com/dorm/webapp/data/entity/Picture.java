package com.dorm.webapp.data.entity;

import com.dorm.webapp.data.shared.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Picture extends BaseEntity {

    private User owner;
    private Room ofRoom;
    private User ofUser;

    private String url;

    @ManyToOne
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @ManyToOne
    public Room getOfRoom() {
        return ofRoom;
    }

    public void setOfRoom(Room ofRoom) {
        this.ofRoom = ofRoom;
    }

    @ManyToOne
    public User getOfUser() {
        return ofUser;
    }

    public void setOfUser(User ofUser) {
        this.ofUser = ofUser;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
