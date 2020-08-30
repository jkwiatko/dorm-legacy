package com.dorm.backend.message.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LatestMessagesRequest {
    Long chatId;
    LocalDate lastMessageDate;
}
