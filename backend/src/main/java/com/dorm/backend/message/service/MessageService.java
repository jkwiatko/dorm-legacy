package com.dorm.backend.message.service;


import com.dorm.backend.message.dto.*;

import java.util.List;

public interface MessageService {

    List<ChatPreviewDTO> getChatMates();

    boolean checkIfHasChat(Long userId);

    void addToMateToChat(Long userId);

    ChatDTO getChat(Long chatId);

    List<MessageDTO> getLatestMessages(LatestMessagesRequest latestMessagesRequest);

    void sendMessageToMate(MessageRequest messageDTO);
}
