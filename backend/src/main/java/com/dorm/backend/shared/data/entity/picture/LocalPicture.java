package com.dorm.backend.shared.data.entity.picture;

import lombok.*;

@Getter
@Setter
@Builder
public class LocalPicture extends LocalPictureEntity {

    private byte[] picture;

    public LocalPicture() {
    }

    public LocalPicture(byte[] picture) {
        this.picture = picture;
    }
}
