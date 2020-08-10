package com.dorm.backend.profile.service;

import com.dorm.backend.profile.dto.ProfileDTO;
import com.dorm.backend.shared.data.entity.User;
import com.dorm.backend.shared.data.entity.picture.LocalPicture;
import com.dorm.backend.shared.data.enums.Inclination;
import com.dorm.backend.shared.service.UserService;
import com.dorm.backend.shared.service.storage.LocalPictureService;
import com.dorm.backend.shared.service.storage.PictureLocalStorage;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LocalProfileService implements ProfileService {

    ModelMapper modelMapper;
    UserService userService;
    LocalPictureService pictureService;

    public LocalProfileService(
            ModelMapper modelMapper,
            UserService userService,
            LocalPictureService pictureService
    ) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.pictureService = pictureService;
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
                .collect(Collectors.toList())
        );
        user.getInclinations().clear();
        user.getInclinations().addAll(profileDTO.getInclinations()
                .stream()
                .distinct()
                .map(Inclination::getEnum)
                .collect(Collectors.toList())
        );
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

    private void setPictureDetails(User user) {
        user.getProfilePictures().forEach(picture -> {
            picture.setOwner(user);
            picture.setOfUser(user);
        });
    }
}
