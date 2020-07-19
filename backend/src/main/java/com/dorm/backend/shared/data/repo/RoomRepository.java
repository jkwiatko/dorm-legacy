package com.dorm.backend.shared.data.repo;

import com.dorm.backend.shared.data.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query(value = "SELECT r FROM Room r JOIN r.address adr JOIN adr.city c WHERE c.name = ?1")
    List<Room> findAllByCity(String cityName);
}
