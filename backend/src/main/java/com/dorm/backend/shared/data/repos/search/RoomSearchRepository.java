package com.dorm.backend.shared.data.repos.search;

import com.dorm.backend.room.dtos.RoomSearchCriteria;
import com.dorm.backend.shared.data.entities.Room;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@Repository
public class RoomSearchRepository {

    @PersistenceContext
    EntityManager entityManager;

    public List<Room> findRoomUsingCriteria(RoomSearchCriteria criteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Room> criteriaQuery = criteriaBuilder.createQuery(Room.class);
        Root<Room> room = criteriaQuery.from(Room.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(
                criteriaBuilder.equal(
                        room.join("address").join("city").get("name"),
                        criteria.getCityName()
                )
        );
        criteria.getRoomName()
                .filter(roomName -> !roomName.isEmpty())
                .map(roomName -> criteriaBuilder.like(room.get("name"), roomName))
                .ifPresent(predicates::add);
        criteria.getStartingDate()
                .map(start -> criteriaBuilder.lessThanOrEqualTo(room.get("availableFrom"), start))
                .ifPresent(predicates::add);
        criteria.getDuration()
                .map(duration -> criteriaBuilder.lessThanOrEqualTo(room.get("minDuration"), duration))
                .ifPresent(predicates::add);
        criteria.getMaxPrice()
                .map(price -> criteriaBuilder.lessThanOrEqualTo(room.get("monthlyPrice"), price))
                .ifPresent(predicates::add);
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
