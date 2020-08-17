package com.dorm.backend.profile.dto;


import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class ProfileSearchCriteria {

    private Long roomId;

    private String gender;

    private Integer minAge;

    private Integer maxAge;

    private String workAndUniversity;

    private List<String> inclinations;

    public Optional<Long> getRoomId() {
        return Optional.ofNullable(roomId);
    }

    public Optional<String> getGender() {
        return Optional.ofNullable(gender);
    }

    public Optional<Integer> getMinAge() {
        return Optional.ofNullable(minAge);
    }

    public Optional<Integer> getMaxAge() {
        return Optional.ofNullable(maxAge);
    }

    public Optional<String> getWorkAndUniversity() {
        return Optional.ofNullable(workAndUniversity);
    }

    public Optional<List<String>> getInclinations() {
        return Optional.ofNullable(inclinations);
    }
}
