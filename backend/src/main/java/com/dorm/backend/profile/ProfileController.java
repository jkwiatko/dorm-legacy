package com.dorm.backend.profile;

import com.dorm.backend.profile.dto.ProfileDTO;
import com.dorm.backend.profile.dto.ProfileSearchCriteria;
import com.dorm.backend.profile.service.ProfileService;
import com.dorm.backend.shared.data.dto.ProfilePreviewDTO;
import com.dorm.backend.shared.data.enums.Inclination;
import com.dorm.backend.shared.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/search/roommates/")
    public ResponseEntity<List<ProfilePreviewDTO>> getPossibleRoommatesUsingCriteria(
            @RequestBody ProfileSearchCriteria profileSearchCriteria
    ) {
        return ResponseEntity.ok().body(profileService.getPossibleRoommateProfiles(profileSearchCriteria));
    }

    @GetMapping("/characteristics")
    public ResponseEntity<List<String>> getCharacteristics() {
        return ResponseEntity.ok().body(
                Arrays.stream(Inclination.values())
                        .map(Inclination::toString)
                        .collect(Collectors.toList())
        );
    }
}
