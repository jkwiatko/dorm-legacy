package com.dorm.backend.message.service;

import com.dorm.backend.message.dto.*;
import com.dorm.backend.shared.data.dto.ProfilePreviewDTO;
import com.dorm.backend.shared.data.entity.User;
import com.dorm.backend.shared.data.entity.message.Chat;
import com.dorm.backend.shared.data.entity.message.Message;
import com.dorm.backend.shared.data.repo.ChatRepository;
import com.dorm.backend.shared.data.repo.MessageRepository;
import com.dorm.backend.shared.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public MessageServiceImpl(ChatRepository chatRepository,
                              MessageRepository messageRepository,
                              UserService userService,
                              ModelMapper modelMapper) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ChatPreviewDTO> getChatMates() {
        Long userId = userService.getCurrentAuthenticatedUser().getId();
        return chatRepository.findAllByOwner_IdOrMate_Id(userId, userId).stream()
                .map(chat -> mapToChatPreview(chat, userId))
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkIfHasChat(Long userId) {
        return !chatRepository.findAllByOwner_IdOrMate_Id(userService.getCurrentAuthenticatedUser().getId(), userId)
                .isEmpty();
    }

    @Override
    public void addToMateToChat(Long userId) {
        Chat chat = new Chat();
        chat.setOwner(userService.getCurrentAuthenticatedUser());
        chat.setMate(userService.getUser(userId));
        chatRepository.save(chat);
    }

    @Override
    public ChatDTO getChat(Long chatId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(EntityNotFoundException::new);
        return ChatDTO.builder()
                .id(chat.getId())
                .messages(chat.getMessages().stream()
                        .map(this::mapToMessageDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public List<MessageDTO> getLatestMessages(LatestMessagesRequest latestMessagesRequest) {
        return messageRepository.findAllByCreatedDateAndChat_Id(
                java.util.Date.from(latestMessagesRequest.getLastMessageDate()
                        .atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant()),
                latestMessagesRequest.getChatId())
                .stream()
                .map(this::mapToMessageDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void sendMessageToMate(MessageRequest messageRequest) {
        Chat chat = chatRepository.findById(messageRequest.getChatId()).orElseThrow(EntityNotFoundException::new);
        chat.getMessages().add(Message.builder()
                .chat(chat)
                .from(userService.getCurrentAuthenticatedUser())
                .text(messageRequest.getText()).build()
        );
        chatRepository.save(chat);
    }

    private MessageDTO mapToMessageDTO(Message message) {
        return MessageDTO.builder()
                .text(message.getText())
                .time(message.getCreatedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .from(getSenderName(message))
                .build();
    }

    private String getSenderName(Message message) {
        User from = message.getFrom();
        if (userService.getCurrentAuthenticatedUser().getId().equals(from.getId())) {
            return "You";
        }
        return from.getFirstName() + " " + from.getLastName();
    }

    private ChatPreviewDTO mapToChatPreview(Chat chat, Long currentUserId) {
        ChatPreviewDTO dto = modelMapper.map(chat, ChatPreviewDTO.class);
        dto.setProfilePreview(modelMapper.map(extractOtherUser(chat, currentUserId), ProfilePreviewDTO.class));
        return dto;
    }

    private static User extractOtherUser(Chat chat, Long currentUserId) {
        if (chat.getOwner().getId().equals(currentUserId)) {
            return chat.getMate();
        }
        return chat.getOwner();
    }
}
