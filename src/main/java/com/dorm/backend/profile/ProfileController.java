package com.dorm.backend.profile;

import com.dorm.backend.profile.dto.ProfileDTO;
import com.dorm.backend.shared.services.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final Log logger = LogFactory.getLog(this.getClass());

    private final UserService userService;

    public ProfileController (UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getProfileForId(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.getUserProfile(id));
    }

    @GetMapping("/edit")
    public ResponseEntity<ProfileDTO> getEditedProfile() {
        return ResponseEntity.ok().body(userService.getUserProfile(userService.getCurrentAuthenticatedUser().getId()));
    }

    @PostMapping("/edit")
    public ResponseEntity<Void> EditProfile(@RequestBody ProfileDTO profile) {
        userService.editCurrentAuthenticatedUser(profile);
        return ResponseEntity.ok().build();
    }
}
