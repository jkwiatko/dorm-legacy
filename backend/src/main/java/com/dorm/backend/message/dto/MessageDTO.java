package com.dorm.backend.message.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MessageDTO {
    private String from;
    private String text;
    private Date time;
}
