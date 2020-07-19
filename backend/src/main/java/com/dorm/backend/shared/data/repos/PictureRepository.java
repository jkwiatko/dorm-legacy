package com.dorm.backend.shared.data.repos;

import com.dorm.backend.shared.data.entities.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
    @Query(value = "SELECT MAX(p.id) FROM Picture p")
    Optional<Long> getLastIdAsString();
}
