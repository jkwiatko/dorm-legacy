package com.dorm.backend.shared.data.dto;

import com.dorm.backend.shared.data.enums.Gender;
import lombok.Data;

import java.util.Date;

@Data
public class ProfilePreviewDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private Date birthDate;

    private Gender gender;

    private PictureDTO picture;

    private String studyingAt;

    private String workingIn;

}
