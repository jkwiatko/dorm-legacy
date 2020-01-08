package com.dorm.webapp.profile;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/edit")
    public ResponseEntity<Void> EditProfile(@RequestBody ProfileDTO profile) {
        profileService.updateUserDetails(profile);
        return ResponseEntity.ok().build();
    }

}
