package com.dorm.backend.message.dto;

import com.dorm.backend.shared.data.dto.ProfilePreviewDTO;
import lombok.Data;

@Data
public class ChatPreviewDTO {
    Long id;
    ProfilePreviewDTO profilePreview;
}
