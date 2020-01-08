package com.dorm.webapp.util.image;

import com.dorm.webapp.data.entity.Picture;
import com.dorm.webapp.data.entity.Room;
import com.dorm.webapp.data.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileSystemImageStorage implements ImageStorage {

    @Override
    public void storeImage(Picture picture) {
    }

    @Override
    public Picture getProfileImage(User user) {
        return null;
    }

    @Override
    public List<Picture> getRoomImages(Room room) {
        return null;
    }
    
}
