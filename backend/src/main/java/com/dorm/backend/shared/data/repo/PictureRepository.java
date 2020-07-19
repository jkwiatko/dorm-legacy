package com.dorm.backend.shared.data.repo;

import com.dorm.backend.shared.data.entity.picture.LocalPictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PictureRepository extends JpaRepository<LocalPictureEntity, Long> {
    @Query(value = "SELECT MAX(p.id) FROM LocalPictureEntity p")
    Optional<Long> getLastIdAsString();
}
