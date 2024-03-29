package com.dorm.backend.profile.dto;

import com.dorm.backend.shared.data.dto.PictureDTO;
import com.dorm.backend.shared.data.dto.RoomPreviewDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProfileDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private List<PictureDTO> profilePictures;

    private Date birthDate;

    private String description;

    private String gender;

    private String workingIn;

    private String studyingAt;

    private List<String> interests;

    private List<String> inclinations;

    private String cleaningPolicy;

    private String smokingPolicy;

    private String petPolicy;

    private String guestsPolicy;

    private Long rentedRoomId;

    private List<RoomPreviewDTO> ownedRooms;
}
