package com.dorm.backend.shared.data.dtos;

import lombok.Data;

@Data
public class PictureDTO {

    private String  base64String;

    private String  name;

    private Integer pictureOrder;

}
