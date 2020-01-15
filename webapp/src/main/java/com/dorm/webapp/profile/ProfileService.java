package com.dorm.webapp.profile;

import com.dorm.webapp.data.entity.Picture;
import com.dorm.webapp.data.entity.User;
import com.dorm.webapp.data.repo.PictureRepository;
import com.dorm.webapp.data.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class ProfileService {
    private final Log logger = LogFactory.getLog(this.getClass());
    private static final String imageStoragePath = "/dorm/image_storage";

    private final UserService userService;
    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;

    public ProfileService(
            ModelMapper modelMapper,
            UserService userService,
            PictureRepository pictureRepository
    ) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.pictureRepository = pictureRepository;
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
            pictureRepository.save(picture);
            savePictureToLocalSystemFile(picture);
        } else {
            throw new FileNameInUseException();
        }
    }

    public void savePictureToLocalSystemFile(Picture picture) {
        File pictureFile = new File(
                System.getProperties().getProperty("user.home")
                +  imageStoragePath
                + picture.getUrl()
                + picture.getPictureName()
        );
        try (InputStream inputStream = new ByteArrayInputStream(picture.getPicture())) {
            if(!pictureFile.getParentFile().exists()) {
                pictureFile.getParentFile().mkdirs();
            }
            BufferedImage bImage = ImageIO.read(inputStream);
            ImageIO.write(bImage, "jpg", pictureFile);
        } catch (IOException e) {
            logger.error(String.format("IOException while saving image to local file storage with path:%s", pictureFile.getPath()), e);
        }
    }
}
