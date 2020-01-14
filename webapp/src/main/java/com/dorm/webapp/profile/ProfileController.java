package com.dorm.webapp.profile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final Log logger = LogFactory.getLog(this.getClass());

    ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/edit")
    public ResponseEntity<String> EditProfile(@RequestBody ProfileDTO profile) {
        try {
            profileService.updateUserDetails(profile);
        } catch (FileNameInUseException e) {
            logger.error("user submitted picture with same name");
            return ResponseEntity.badRequest().body("File name is already taken for this user");
        }
        return ResponseEntity.ok().build();
    }

}
