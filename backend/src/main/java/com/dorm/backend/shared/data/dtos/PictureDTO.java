package com.dorm.backend.shared.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PictureDTO {

    private String base64String;

    private String name;

    private Integer pictureOrder;

}
