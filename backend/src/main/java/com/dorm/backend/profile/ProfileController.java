package com.dorm.backend.profile;

import com.dorm.backend.profile.dto.ProfileDTO;
import com.dorm.backend.shared.data.enums.EUserCharacteristic;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
    public ResponseEntity<Void> editCurrentUserProfile(@RequestBody ProfileDTO profile) {
        userService.editCurrentUserProfile(profile);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/characteristics")
    public ResponseEntity<List<String>> getCharacteristics() {
        return ResponseEntity.ok()
                .body(Arrays.stream(EUserCharacteristic.values()).map(EUserCharacteristic::toString).collect(Collectors.toList()));
    }
}
