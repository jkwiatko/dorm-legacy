package com.dorm.backend.profile.service;

import com.dorm.backend.profile.dto.ProfileDTO;

public interface ProfileService {

    ProfileDTO getUserProfile(Long id);

    void editProfile(ProfileDTO profileDTO);

}
