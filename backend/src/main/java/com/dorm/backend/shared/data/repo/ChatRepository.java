package com.dorm.backend.shared.data.repo;

import com.dorm.backend.shared.data.entity.message.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByOwner_IdOrMate_Id(Long ownerId, Long mateId);
}
