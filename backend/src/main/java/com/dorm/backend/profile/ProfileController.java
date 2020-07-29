package com.dorm.backend.profile;

import com.dorm.backend.profile.dto.ProfileDTO;
import com.dorm.backend.profile.service.ProfileService;
import com.dorm.backend.shared.data.enums.UserCharacteristic;
import com.dorm.backend.shared.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final UserService userService;
    private final ProfileService profileService;

    public ProfileController(UserService userService, ProfileService profileService) {
        this.userService = userService;
        this.profileService = profileService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getProfileForId(@PathVariable Long id) {
        return ResponseEntity.ok().body(profileService.getUserProfile(id));
    }

    @GetMapping("/edit")
    public ResponseEntity<ProfileDTO> getEditedProfile() {
        return ResponseEntity.ok().body(
                profileService.getUserProfile(userService.getCurrentAuthenticatedUser().getId())
        );
    }

    @PostMapping("/edit")
    public ResponseEntity<Void> editCurrentUserProfile(@RequestBody ProfileDTO profile) {
        profileService.editProfile(profile);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/characteristics")
    public ResponseEntity<List<String>> getCharacteristics() {
        return ResponseEntity.ok().body(
                Arrays.stream(UserCharacteristic.values())
                        .map(UserCharacteristic::toString)
                        .collect(Collectors.toList())
        );
    }
}
