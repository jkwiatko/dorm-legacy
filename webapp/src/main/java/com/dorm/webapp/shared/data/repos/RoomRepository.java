package com.dorm.webapp.shared.data.repos;

import com.dorm.webapp.shared.data.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
