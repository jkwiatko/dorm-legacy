package com.dorm.backend.shared.data.entities;

import com.dorm.backend.shared.data.entities.base.BaseEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

@Entity
public class Picture extends BaseEntity {
    public static final String imageStoragePath = "/dorm/image_storage";
    private final Log logger = LogFactory.getLog(this.getClass());

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

    public void setPicture(byte[] picture) {
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

    @PostLoad
    public void autoLoadPicture() {
        File pictureFile = new File(
                System.getProperties().getProperty("user.home")
                        + imageStoragePath
                        + url
                        + pictureName
        );
        try (FileInputStream fileInputStream = new FileInputStream(pictureFile)) {
            picture = Files.readAllBytes(pictureFile.toPath());
        } catch (IOException e) {
            logger.error(String.format("Couldn't load file with path name:%s", pictureFile.getPath()),e);
        }
    }

    @PostRemove
    public void autoRemovePicture() {
        File pictureFile = new File(
                System.getProperties().getProperty("user.home")
                        + imageStoragePath
                        + url
                        + pictureName
        );
        if (pictureFile.exists()) {
            if (!pictureFile.delete()) {
                logger.error(String.format("Couldn't delete file with path name:%s", pictureFile.getPath()), new Exception("INVALID FILE"));
            }
        }
    }
}
