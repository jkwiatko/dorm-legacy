package com.dorm.webapp.shared.data.repos;

import com.dorm.webapp.shared.data.entities.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
}
