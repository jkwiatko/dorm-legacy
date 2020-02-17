package com.dorm.backend.shared.data.repos;

import com.dorm.backend.shared.data.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query(value = "SELECT r FROM Room r JOIN r.address a WHERE a.city = ?1")
    List<Room> findAllByCity(String cityName);
}
