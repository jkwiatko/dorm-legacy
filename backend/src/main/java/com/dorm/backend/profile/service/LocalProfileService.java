package com.dorm.backend.profile.service;

import com.dorm.backend.profile.dto.ProfileDTO;
import com.dorm.backend.profile.dto.ProfileSearchCriteria;
import com.dorm.backend.shared.data.dto.PictureDTO;
import com.dorm.backend.shared.data.dto.ProfilePreviewDTO;
import com.dorm.backend.shared.data.entity.User;
import com.dorm.backend.shared.data.entity.picture.LocalPicture;
import com.dorm.backend.shared.data.enums.Inclination;
import com.dorm.backend.shared.data.repo.search.ProfileSearchRepository;
import com.dorm.backend.shared.service.UserService;
import com.dorm.backend.shared.service.storage.LocalPictureService;
import com.dorm.backend.shared.service.storage.PictureLocalStorage;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class LocalProfileService implements ProfileService {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final LocalPictureService pictureService;
    private final ProfileSearchRepository searchRepository;

    public LocalProfileService(
            ModelMapper modelMapper,
            UserService userService,
            LocalPictureService pictureService,
            ProfileSearchRepository searchRepository
    ) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.pictureService = pictureService;
        this.searchRepository = searchRepository;
    }

    @Override
    public void editProfile(ProfileDTO profileDTO) {
        User user = userService.getCurrentAuthenticatedUser();
        modelMapper.map(profileDTO, user);

        user.getInterests().clear();
        user.getInterests().addAll(profileDTO.getInterests()
                .stream()
                .map(interest -> StringUtils.capitalize(interest.toLowerCase()))
                .distinct()
                .collect(Collectors.toList()));
        user.getInclinations().clear();
        user.getInclinations().addAll(profileDTO.getInclinations()
                .stream()
                .distinct()
                .map(Inclination::getEnum)
                .collect(Collectors.toList()));

        List<LocalPicture> newProfilePictures = pictureService.mapToLocalPictures(profileDTO.getProfilePictures());
        user.getProfilePictures().clear();
        user.getProfilePictures().addAll(newProfilePictures);
        setPictureDetails(user);

        userService.updateUser(user);
        newProfilePictures.forEach(PictureLocalStorage::savePicture);
    }

    @Override
    public ProfileDTO getUserProfile(Long id) {
        User user = userService.getUser(id);
        return modelMapper.map(user, ProfileDTO.class);
    }

    @Override
    public List<ProfilePreviewDTO> getPossibleRoommateProfiles(ProfileSearchCriteria profileSearchCriteria) {
        return searchRepository.findProfileUsingCriteria(profileSearchCriteria).stream()
                .filter(user -> inclinationsFilter(user, profileSearchCriteria))
                .map(this::mapUserToProfilePreview)
                .collect(Collectors.toList());
    }

    private ProfilePreviewDTO mapUserToProfilePreview(User user) {
        ProfilePreviewDTO dto = modelMapper.map(user, ProfilePreviewDTO.class);
        user.getProfilePictures().stream()
                .findFirst()
                .ifPresent(picture -> dto.setPicture(modelMapper.map(picture, PictureDTO.class)));
        return dto;
    }

    private boolean inclinationsFilter(User user, ProfileSearchCriteria criteria) {
        return user.getInclinations().containsAll(
                criteria.getInclinations()
                        .map(Collection::stream)
                        .map(stream -> stream.map(Inclination::getEnum)
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList()))
                        .orElse(Collections.emptyList()));
    }

    private static void setPictureDetails(User user) {
        user.getProfilePictures().forEach(picture -> {
            picture.setOwner(user);
            picture.setOfUser(user);
        });
    }
}
