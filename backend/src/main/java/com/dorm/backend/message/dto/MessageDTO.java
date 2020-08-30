package com.dorm.backend.message.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MessageDTO {
    private String from;
    private String text;
    private LocalDate time;
}
