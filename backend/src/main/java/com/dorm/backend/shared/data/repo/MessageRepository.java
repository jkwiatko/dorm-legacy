package com.dorm.backend.shared.data.repo;

import com.dorm.backend.shared.data.entity.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByCreatedDateAndChat_Id(Date createdDate, Long chatId);
}
