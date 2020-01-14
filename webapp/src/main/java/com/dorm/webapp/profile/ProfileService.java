package com.dorm.webapp.profile;

import com.dorm.webapp.data.entity.Picture;
import com.dorm.webapp.data.entity.User;
import com.dorm.webapp.data.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public ProfileService(
            ModelMapper modelMapper,
            UserService userService
    ) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    public void updateUserDetails(ProfileDTO profile) throws FileNameInUseException {
        User user = userService.getCurrentAuthenticatedUser();
        modelMapper.map(profile, user);
        addProfilePicture(user, profile.getProfilePicture());
        userService.updateUser(user);
    }

    private void addProfilePicture(User user, ProfilePictureDTO pictureDTO) throws FileNameInUseException {
        boolean filenameInUse = user.getProfilePictures()
                .stream()
                .anyMatch(img -> img.getPictureName().equals(pictureDTO.getName()));

        if (!filenameInUse) {
            Picture picture = modelMapper.map(pictureDTO, Picture.class);
            picture.setOfUser(user);
            picture.setOwner(user);
            user.getProfilePictures().add(picture);
        } else {
            throw new FileNameInUseException();
        }
    }
}
