package com.dorm.webapp.shared.data.entities;

import com.dorm.webapp.shared.data.entities.base.BaseEntity;

import javax.persistence.*;
import java.io.File;

@Entity
public class Picture extends BaseEntity {

    private User owner;
    private Room ofRoom;
    private User ofUser;

    private String url;
    private String pictureName;
    private byte[] picture;

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

    @Column(unique = true)
    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    @Transient
    public byte[] getPicture() {
        return picture;
    }

    public void persistPicture(byte[] picture) {
        this.picture = picture;
    }

    public static String produceHashPictureDirectoryFilename(String fileName) {
        int hash = fileName.hashCode();
        int mask = 255;
        int firstDir = hash & mask;
        int secondDir = (hash >> 8) & mask;
        return File.separator +
                String.format("%03d", firstDir) +
                File.separator +
                String.format("%03d", secondDir) +
                File.separator;
    }
}
