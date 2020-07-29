package com.dorm.backend.shared.data.dto;

import com.dorm.backend.shared.data.entity.picture.LocalPictureEntity;
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

    private LocalPictureEntity profilePictures;

    private String studyingAt;

}
