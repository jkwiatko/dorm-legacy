package com.dorm.webapp.profile;

import com.dorm.webapp.shared.data.entities.User;
import com.dorm.webapp.shared.services.PictureService;
import com.dorm.webapp.shared.services.UserService;
import com.dorm.webapp.profile.dto.ProfileDTO;
import com.dorm.webapp.profile.exception.FileNameInUseException;
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
        User user = userService.getCurrentAuthenticatedUser();
        return ResponseEntity.ok().body(modelMapper.map(user, ProfileDTO.class));
    }

    @PostMapping("/edit")
    public ResponseEntity<String> EditProfile(@RequestBody ProfileDTO profile) {
        try {
            User user = userService.getCurrentAuthenticatedUser();
            modelMapper.map(profile, user);
            pictureService.addProfilePicture(user, profile.getProfilePicture());
            userService.updateUser(user);
        } catch (FileNameInUseException e) {
            logger.error("user submitted picture with same name");
            return ResponseEntity.badRequest().body("File name is already taken for this user");
        }
        return ResponseEntity.ok().build();
    }
}
