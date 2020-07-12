package com.dorm.backend.shared.data.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PictureDTO {

    private String  base64String;

    private String  name;

    private Integer pictureOrder;

}
