package com.dorm.backend.shared.services;

import com.dorm.backend.auth.UserPrincipal;
import com.dorm.backend.auth.jwt.Credentials;
import com.dorm.backend.profile.dto.ProfileDTO;
import com.dorm.backend.shared.data.entities.User;
import com.dorm.backend.shared.data.repos.UserRepository;
import com.dorm.backend.shared.enums.EUserCharacteristic;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final ModelMapper modelMapper;
    private final PictureLocalStorage pictureLocalStorage;
    private final UserRepository userRepository;

    UserService(
            ModelMapper modelMapper,
            PictureLocalStorage pictureLocalStorage,
            UserRepository userRepository
    ) {
        this.modelMapper = modelMapper;
        this.pictureLocalStorage = pictureLocalStorage;
        this.userRepository = userRepository;
    }

    public User getCurrentAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!(principal instanceof UserPrincipal)) {
            throw new InsufficientAuthenticationException("Couldn't not retrieve currently logged in user");
        }
        return getUser(((UserPrincipal) principal).getId());
    }

    public void editCurrentAuthenticatedUser(ProfileDTO profile) {
        User user = getCurrentAuthenticatedUser();
        modelMapper.map(profile, user);

        user.getInterests().clear();
        user.getInterests().addAll(profile.getInterests()
                .stream()
                .map(interest -> StringUtils.capitalize(interest.toLowerCase()))
                .distinct()
                .collect(Collectors.toList())
        );
        user.getInclinations().clear();
        user.getInclinations().addAll(profile.getInclinations()
                .stream()
                .distinct()
                .map(EUserCharacteristic::getEnum)
                .collect(Collectors.toList())
        );

        setPictureDetails(user);
        user.getProfilePictures().stream()
                .filter(picture -> Objects.nonNull(picture.getPicture()))
                .forEach(pictureLocalStorage::savePicture);
        updateUser(user);
    }

    public ProfileDTO getUserProfile(Long id) {
        User user = getUser(id);
        user.getProfilePictures().forEach(PictureLocalStorage::loadPictureFromFileSystem);
        return modelMapper.map(user, ProfileDTO.class);
    }

    public User findByUsername(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userRepository.findAll());
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

    public void  deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void setPictureDetails(User user) {
        user.getProfilePictures().forEach(picture -> {
                picture.setOwner(user);
                picture.setOfUser(user);
        });
    }
}
