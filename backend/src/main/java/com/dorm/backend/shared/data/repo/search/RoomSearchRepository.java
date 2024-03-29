package com.dorm.backend.shared.data.repo.search;

import com.dorm.backend.shared.error.exc.NoSearchTypeSpecifiedException;
import com.dorm.backend.shared.service.UserService;
import com.dorm.backend.room.dto.RoomSearchCriteria;
import com.dorm.backend.shared.data.entity.Room;
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

        switch (criteria.getSearchType()) {
            case RESERVED_OFFER:
                addReservedRoomPredicates(criteriaBuilder, room, predicates);
                break;
            case OWN_OFFER:
                addCurrentUserOffersPredicates(criteriaBuilder, room, predicates);
                break;
            case SEARCHED_OFFER:
                addSearchedOffersPredicates(criteriaBuilder, room, predicates, criteria.getCityName());
                break;
            case RENTED_OFFER:
                addRentedRoomPredicates(criteriaBuilder, room, predicates);
                break;
            default:
                throw new NoSearchTypeSpecifiedException();
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

    private void addCurrentUserOffersPredicates(CriteriaBuilder criteriaBuilder,
                                                Root<Room> room,
                                                List<Predicate> predicates
    ) {
        predicates.add(
                criteriaBuilder.equal(
                        room.join("owner").get("id"),
                        userService.getCurrentAuthenticatedUser().getId()));
        skipAlreadyRentedRooms(criteriaBuilder, room, predicates);
    }

    private void addSearchedOffersPredicates(CriteriaBuilder criteriaBuilder,
                                             Root<Room> room,
                                             List<Predicate> predicates,
                                             String city
    ) {
        room.fetch("possibleRoommates", JoinType.LEFT);
        predicates.add(
                criteriaBuilder.isNotMember(
                        userService.getCurrentAuthenticatedUser(),
                        room.get("possibleRoommates"))
        );
        predicates.add(
                criteriaBuilder.notEqual(
                        room.join("owner").get("id"),
                        userService.getCurrentAuthenticatedUser().getId()));
        predicates.add(criteriaBuilder.isNull(
                room.join("rentee", JoinType.LEFT).get("id")));
        predicates.add(criteriaBuilder.equal(
                room.join("address").join("city").get("name"),
                city
        ));
        skipAlreadyRentedRooms(criteriaBuilder, room, predicates);
    }

    private void addReservedRoomPredicates(CriteriaBuilder criteriaBuilder,
                                           Root<Room> room,
                                           List<Predicate> predicates
    ) {
        predicates.add(
                criteriaBuilder.equal(
                        room.join("possibleRoommates").get("id"),
                        userService.getCurrentAuthenticatedUser().getId()));
        predicates.add(
                criteriaBuilder.notEqual(
                        room.join("owner").get("id"),
                        userService.getCurrentAuthenticatedUser().getId()));
        skipAlreadyRentedRooms(criteriaBuilder, room, predicates);
    }

    private void addRentedRoomPredicates(CriteriaBuilder criteriaBuilder, Root<Room> room, List<Predicate> predicates) {
        predicates.add(
                criteriaBuilder.and(
                        criteriaBuilder.isNotNull(room.join("rentee")),
                        criteriaBuilder.equal(
                                room.join("owner").get("id"),
                                userService.getCurrentAuthenticatedUser().getId()
                        )
                )
        );
    }

    private void skipAlreadyRentedRooms(CriteriaBuilder criteriaBuilder, Root<Room> room, List<Predicate> predicates) {
        predicates.add(criteriaBuilder.isNull(room.join("rentee", JoinType.LEFT)));
    }
}
