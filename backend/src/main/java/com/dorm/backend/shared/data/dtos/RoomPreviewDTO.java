package com.dorm.backend.shared.data.dtos;

import lombok.Data;

@Data
public class RoomPreviewDTO {

    private Long id;

    private String name;

    private String availableFrom;

    private Integer minDuration;

    private Integer roomsNumber;

    private PictureDTO picture;

}
