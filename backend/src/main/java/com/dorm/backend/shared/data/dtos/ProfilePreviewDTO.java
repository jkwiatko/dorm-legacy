package com.dorm.backend.shared.data.dtos;

import com.dorm.backend.shared.data.entities.Picture;
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

    private Picture profilePictures;

    private String studyingAt;

}
