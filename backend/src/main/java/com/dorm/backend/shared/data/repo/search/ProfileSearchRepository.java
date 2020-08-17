package com.dorm.backend.shared.data.repo.search;

import com.dorm.backend.profile.dto.ProfileSearchCriteria;
import com.dorm.backend.shared.data.entity.User;
import com.dorm.backend.shared.data.enums.Gender;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProfileSearchRepository {

    @PersistenceContext
    EntityManager entityManager;

    public List<User> findProfileUsingCriteria(ProfileSearchCriteria criteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> user = criteriaQuery.from(User.class);
        List<Predicate> predicates = new ArrayList<>();

        criteria.getGender()
                .map(Gender::valueOf)
                .map(gender -> criteriaBuilder.equal(user.get("gender"), gender))
                .ifPresent(predicates::add);
        criteria.getMinAge()
                .map(minAge -> LocalDate.now().minus(Period.ofYears(minAge)))
                .map(minBirthYear -> criteriaBuilder.lessThan(user.get("birthDate"), minBirthYear))
                .ifPresent(predicates::add);
        criteria.getMaxAge()
                .map(maxAge -> LocalDate.now().minus(Period.ofYears(maxAge)))
                .map(minBirthYear -> criteriaBuilder.greaterThan(user.get("birthDate"), minBirthYear))
                .ifPresent(predicates::add);
        criteria.getRoomId()
                .map(roomId -> criteriaBuilder.equal(user.join("possibleRooms").get("id"), roomId))
                .ifPresent(predicates::add);
        criteria.getWorkAndUniversity()
                .map(job -> criteriaBuilder.or(
                        criteriaBuilder.like(user.get("studyingAt"), job + "%"),
                        criteriaBuilder.like(user.get("workingIn"), job + "%")))
                .ifPresent(predicates::add);

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
