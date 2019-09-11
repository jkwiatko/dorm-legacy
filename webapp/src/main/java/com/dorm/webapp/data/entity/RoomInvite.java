package com.dorm.webapp.data.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class RoomInvite extends BaseEntity {
    private Room room;
    private User toUser;
    private String message;
    private boolean accepted;

    @ManyToOne
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @ManyToOne
    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
