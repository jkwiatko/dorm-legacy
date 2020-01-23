package com.dorm.backend.profile;

import com.dorm.backend.shared.data.entities.User;
import com.dorm.backend.shared.services.PictureService;
import com.dorm.backend.shared.services.UserService;
import com.dorm.backend.profile.dto.ProfileDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final Log logger = LogFactory.getLog(this.getClass());

    private final UserService userService;
    private final PictureService pictureService;
    private final ModelMapper modelMapper;

    public ProfileController (
            UserService userService,
            PictureService pictureService,
            ModelMapper modelMapper
    ) {
        this.userService = userService;
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/edit")
    public ResponseEntity<ProfileDTO> getEditedProfile() {
        return ResponseEntity.ok().body(userService.getUserProfile(userService.getCurrentAuthenticatedUser().getId()));
    }

    @PostMapping("/edit")
    public ResponseEntity<Void> EditProfile(@RequestBody ProfileDTO profile) {
        User user = userService.getCurrentAuthenticatedUser();
        modelMapper.map(profile, user);
        if(profile.getProfilePicture() != null) {
            pictureService.addProfilePicture(user, profile.getProfilePicture());
        }
        userService.updateUser(user);
        return ResponseEntity.ok().build();
    }
}
