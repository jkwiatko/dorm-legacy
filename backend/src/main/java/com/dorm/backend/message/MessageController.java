package com.dorm.backend.message;

import com.dorm.backend.message.dto.*;
import com.dorm.backend.message.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api/message")
public class MessageController {

    MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("chats")
    public ResponseEntity<List<ChatPreviewDTO>> getChatMates() {
        return  ResponseEntity.ok(messageService.getChatMates());
    }

    @PatchMapping("chats/add")
    public ResponseEntity<Void> addUserToChats(@RequestBody Long userId) {
        messageService.addToMateToChat(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("chats/check/{userId}")
    public ResponseEntity<Boolean> checkIfUserHasChat(@PathVariable Long userId) {
        return ResponseEntity.ok(messageService.checkIfHasChat(userId));
    }

    @GetMapping("chat/{chatId}")
    public ResponseEntity<ChatDTO> getChat(@PathVariable Long chatId) {
        return ResponseEntity.ok(messageService.getChat(chatId));
    }

    @PostMapping("chat/latest")
    public ResponseEntity<List<MessageDTO>> getLatestMessages(LatestMessagesRequest request) {
        return ResponseEntity.ok(messageService.getLatestMessages(request));
    }

    @PostMapping("send")
    public ResponseEntity<Void> sendMessageToMate(@RequestBody MessageRequest message) {
        messageService.sendMessageToMate(message);
        return ResponseEntity.ok().build();
    }
}
