package com.dorm.backend.shared.data.spec;

import com.dorm.backend.shared.data.entities.Room;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class RoomSpecifications {

    public static Specification<Room> isRoomInTheCity(String cityName) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("address").join("city").get("name"), cityName);
    }

//    TODO
//    public static Specification<Room> isRoomAvailable(Date date) {
//        return (root, criteriaQuery, criteriaBuilder) ->
//                criteriaBuilder.equal(root.join("address").join("city").get("name"), cityName);
//    }
//
//    public static Specification<Room> isDurationLongEnough(Integer duration) {
//        return (root, criteriaQuery, criteriaBuilder) ->
//                criteriaBuilder.equal(root.join("address").join("city").get("name"), cityName);
//    }
//
//    public static Specification<Room> isRoomInTheBudget(Integer budget) {
//        return (root, criteriaQuery, criteriaBuilder) ->
//                criteriaBuilder.equal(root.join("address").join("city").get("name"), cityName);
//    }
//
//    public static Specification<Room> isRoomHasNameLikeThis(String roomName) {
//        return (root, criteriaQuery, criteriaBuilder) ->
//                criteriaBuilder.equal(root.join("address").join("city").get("name"), cityName);
//    }
}
