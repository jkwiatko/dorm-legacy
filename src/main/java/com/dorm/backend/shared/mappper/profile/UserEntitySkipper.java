package com.dorm.backend.shared.mappper.profile;

import com.dorm.backend.profile.dto.ProfileDTO;
import com.dorm.backend.shared.data.entities.User;
import org.modelmapper.PropertyMap;

public class UserEntitySkipper extends PropertyMap<ProfileDTO, User> {
    @Override
    protected void configure() {
        skip(destination.getId());
    }
}
