package com.dorm.webapp.shared.services;

import com.dorm.webapp.shared.data.entities.Picture;
import com.dorm.webapp.shared.data.entities.User;
import com.dorm.webapp.shared.data.repos.PictureRepository;
import com.dorm.webapp.profile.dto.ProfilePictureDTO;
import com.dorm.webapp.profile.exception.FileNameInUseException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
public class PictureService {
    private final Log logger = LogFactory.getLog(this.getClass());
    private static final String imageStoragePath = "/dorm/image_storage";

    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;

    public PictureService(
            PictureRepository pictureRepository,
            ModelMapper modelMapper
    ) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
    }

    public void addProfilePicture(User user, ProfilePictureDTO pictureDTO) throws FileNameInUseException {
        boolean filenameInUse = user.getProfilePictures()
                .stream()
                .anyMatch(img -> img.getPictureName().equals(pictureDTO.getName()));

        if (!filenameInUse) {
            Picture picture = modelMapper.map(pictureDTO, Picture.class);
            picture.setOfUser(user);
            picture.setOwner(user);
            pictureRepository.save(picture);
            savePictureToLocalFileSystem(picture);
        } else {
            throw new FileNameInUseException();
        }
    }

    public void savePictureToLocalFileSystem(Picture picture) {
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
