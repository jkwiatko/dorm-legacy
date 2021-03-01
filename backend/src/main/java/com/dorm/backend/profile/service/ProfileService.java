package com.dorm.backend.profile.service;

import com.dorm.backend.profile.dto.ProfileDTO;
import com.dorm.backend.profile.dto.ProfileSearchCriteria;
import com.dorm.backend.shared.data.dto.ProfilePreviewDTO;

import java.util.List;

public interface ProfileService {

    ProfileDTO getUserProfile(Long id);

    void editProfile(ProfileDTO profileDTO);

    List<ProfilePreviewDTO> getPossibleRoommateProfiles(ProfileSearchCriteria profileSearchCriteria);

}
