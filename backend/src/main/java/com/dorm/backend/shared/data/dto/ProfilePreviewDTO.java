package com.dorm.backend.shared.data.dto;

import com.dorm.backend.shared.data.enums.Gender;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class ProfilePreviewDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private Gender gender;

    private PictureDTO picture;

    private String studyingAt;

    private String workingIn;

}
