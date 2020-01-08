package com.dorm.webapp.profile;

import com.dorm.webapp.data.entity.User;
import com.dorm.webapp.data.repo.UserRepository;
import com.dorm.webapp.data.service.UserService;
import com.dorm.webapp.util.image.ImageStorage;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class ProfileService {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ImageStorage imageStorage;

    public ProfileService(
            ModelMapper modelMapper,
            UserService userService,
            ImageStorage imageStorage
    ) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.imageStorage = imageStorage;
    }

    public void updateUserDetails(ProfileDTO profile) {
        User user = userService.getCurrentAuthenticatedUser();
        modelMapper.map(profile, user);
        userService.updateUser(user);
    }
}
