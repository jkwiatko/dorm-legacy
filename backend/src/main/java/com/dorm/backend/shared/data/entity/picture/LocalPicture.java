package com.dorm.backend.shared.data.entity.picture;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocalPicture extends LocalPictureEntity {
    private byte[] picture;
}
