package com.dorm.backend.message.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LatestMessagesRequest {
    Long chatId;
    Date lastMessageDate;
}
