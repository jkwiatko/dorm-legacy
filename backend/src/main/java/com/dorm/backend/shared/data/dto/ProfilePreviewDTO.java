package com.dorm.backend.shared.data.dto;

import com.dorm.backend.shared.data.entity.picture.LocalPictureEntity;
import com.dorm.backend.shared.data.enums.EGender;
import lombok.Data;

import java.util.Date;

@Data
public class ProfilePreviewDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private Date birthDate;

    private EGender gender;

    private LocalPictureEntity profilePictures;

    private String studyingAt;

}
