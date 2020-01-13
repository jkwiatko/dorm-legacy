package com.dorm.webapp.profile;

import com.dorm.webapp.data.entity.User;
import com.dorm.webapp.data.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public ProfileService(
            ModelMapper modelMapper,
            UserService userService
    ) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    public void updateUserDetails(ProfileDTO profile) {
        User user = userService.getCurrentAuthenticatedUser();
        modelMapper.map(profile, user);
        userService.updateUser(user);
    }
}
