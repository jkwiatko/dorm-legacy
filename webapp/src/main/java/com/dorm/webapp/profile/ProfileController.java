package com.dorm.webapp.profile;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @PostMapping("/edit")
    public ResponseEntity<Void> EditProfile(@RequestParam MultipartFile image) {
        return ResponseEntity.ok().build();
    }

}
