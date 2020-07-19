package com.dorm.backend.shared.data.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocalPicture extends Picture {
    private byte[] picture;
}
