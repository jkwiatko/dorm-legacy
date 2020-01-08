package com.dorm.webapp.util.image;

import com.dorm.webapp.data.entity.Picture;
import com.dorm.webapp.data.entity.Room;
import com.dorm.webapp.data.entity.User;

import java.util.List;

public interface ImageStorage {
    void storeImage (Picture picture);
    Picture getProfileImage (User user);
    List<Picture> getRoomImages (Room room);
}
