package com.dorm.backend.shared.data.repos.search;

import com.dorm.backend.profile.UserService;
import com.dorm.backend.room.dtos.RoomSearchCriteria;
import com.dorm.backend.shared.data.entities.Room;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class RoomSearchRepository {

    @PersistenceContext
    EntityManager entityManager;

    UserService userService;

    public RoomSearchRepository(UserService userService) {
        this.userService = userService;
    }

    public List<Room> findRoomUsingCriteria(RoomSearchCriteria criteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Room> criteriaQuery = criteriaBuilder.createQuery(Room.class);
        Root<Room> room = criteriaQuery.from(Room.class);
        List<Predicate> predicates = new ArrayList<>();

        if (criteria.isLookingForUserOffer()) {
            predicates.add(
                    criteriaBuilder.equal(
                            room.join("owner").get("id"),
                            userService.getCurrentAuthenticatedUser().getId()
                    ));
        } else {
            room.fetch("possibleRoommates", JoinType.LEFT);
            predicates.add(
                    criteriaBuilder.isNotMember(
                            userService.getCurrentAuthenticatedUser(),
                            room.get("possibleRoommates")
                    )
            );
            predicates.add(
                    criteriaBuilder.equal(
                            room.join("address").join("city").get("name"),
                            criteria.getCityName()
                    )
            );
            predicates.add(
                    criteriaBuilder.notEqual(
                            room.join("owner").get("id"),
                            userService.getCurrentAuthenticatedUser().getId()
                    ));
        }
        criteria.getRoomName()
                .filter(roomName -> !roomName.isEmpty())
                .map(roomName -> criteriaBuilder.like(room.get("name"), roomName + "%"))
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
