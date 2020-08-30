package com.dorm.backend.message.dto;

import lombok.Data;

@Data
public class MessageRequest {
    Long chatId;
    String text;
}
