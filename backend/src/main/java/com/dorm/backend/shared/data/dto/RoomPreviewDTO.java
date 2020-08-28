package com.dorm.backend.shared.data.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomPreviewDTO {

    private Long id;

    private String name;

    private String availableFrom;

    private Integer minDuration;

    private Integer roomsNumber;

    private BigDecimal monthlyPrice;

    private PictureDTO picture;

    private Long renteeId;

}
