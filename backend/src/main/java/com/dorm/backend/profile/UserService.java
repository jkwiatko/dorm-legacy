package com.dorm.backend.profile;

import com.dorm.backend.auth.UserPrincipal;
import com.dorm.backend.auth.jwt.Credentials;
import com.dorm.backend.profile.dto.ProfileDTO;
import com.dorm.backend.shared.data.entities.LocalPicture;
import com.dorm.backend.shared.data.entities.User;
import com.dorm.backend.shared.data.enums.EUserCharacteristic;
import com.dorm.backend.shared.data.repos.UserRepository;
import com.dorm.backend.shared.storage.PictureLocalStorage;
import com.dorm.backend.shared.storage.PictureService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final ModelMapper modelMapper;
    private final PictureService pictureService;
    private final UserRepository userRepository;

    UserService(
            ModelMapper modelMapper,
            PictureService pictureService,
            UserRepository userRepository
    ) {
        this.modelMapper = modelMapper;
        this.pictureService = pictureService;
        this.userRepository = userRepository;
    }

    public void editCurrentUserProfile(ProfileDTO profileDTO) {
        User user = getCurrentAuthenticatedUser();
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
                .map(EUserCharacteristic::getEnum)
                .collect(Collectors.toList())
        );
        List<LocalPicture> newProfilePictures = pictureService.mapToLocalPictures(profileDTO.getProfilePictures());
        user.getProfilePictures().clear();
        user.getProfilePictures().addAll(newProfilePictures);
        setPictureDetails(user);

        updateUser(user);
        newProfilePictures.forEach(PictureLocalStorage::savePicture);
    }

    public User getCurrentAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!(principal instanceof UserPrincipal)) {
            throw new InsufficientAuthenticationException("Couldn't not retrieve currently logged in user");
        }
        return getUser(((UserPrincipal) principal).getId());
    }

    public ProfileDTO getUserProfile(Long id) {
        User user = getUser(id);
        return modelMapper.map(user, ProfileDTO.class);
    }

    public void addUser(Credentials credentials) {
        User userToAdd = modelMapper.map(credentials, User.class);
        userRepository.save(userToAdd);
    }

    public User getUser(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User of id: %s not found",id)));
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    private void setPictureDetails(User user) {
        user.getProfilePictures().forEach(picture -> {
                picture.setOwner(user);
                picture.setOfUser(user);
        });
    }
}
