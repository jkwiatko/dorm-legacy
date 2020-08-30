package com.dorm.backend.message.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChatDTO {
    Long id;
    List<MessageDTO> messages;
}
