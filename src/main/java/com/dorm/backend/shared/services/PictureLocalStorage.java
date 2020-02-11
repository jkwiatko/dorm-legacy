package com.dorm.backend.shared.services;

import com.dorm.backend.shared.data.entities.Picture;
import com.dorm.backend.shared.data.repos.PictureRepository;
import com.dorm.backend.shared.error.exc.LoadPictureException;
import com.dorm.backend.shared.error.exc.PersistFileException;
import com.dorm.backend.shared.error.exc.RemovePictureException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.persistence.EntityNotFoundException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;

@Service
public class PictureLocalStorage {
    public static final String imageStoragePath = "/dorm/image_storage";
    private final Log logger = LogFactory.getLog(this.getClass());

    private final PictureRepository pictureRepository;

    public PictureLocalStorage(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    public Picture getPicture(Long id) {
        Picture picture = pictureRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        picture.setPicture(loadPictureFromFileSystem(picture));
        return picture;
    }

    public void savePicture(Picture picture) {
        try {
            persistPictureToFileSystem(picture);
        } catch (IOException e) {
            pictureRepository.delete(picture);
            logger.error("IOException on img saving procedure -> url: " + picture.getUrl(), e);
            throw new PersistFileException();
        }
    }

    public void deletePicture(Long id) {
        Picture picture = pictureRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        removePictureFromFileSystem(picture);
    }

    public void persistPictureToFileSystem(Picture picture) throws IOException {
        File pictureFile = new File(
                System.getProperties().getProperty("user.home")
                        + imageStoragePath
                        + picture.getUrl()
                        + picture.getPictureName()
        );

        try (InputStream inputStream = new ByteArrayInputStream(picture.getPicture())) {
            if(
                    !pictureFile.getParentFile().exists() &&
                            !pictureFile.getParentFile().mkdirs()
            ) {
                throw new IOException();
            }
            BufferedImage bImage = ImageIO.read(inputStream);
            ImageIO.write(bImage, "jpg", pictureFile);
        }
    }

    public byte[] loadPictureFromFileSystem(Picture picture) {
        File pictureFile = new File(
                System.getProperties().getProperty("user.home")
                        + imageStoragePath
                        + picture.getUrl()
                        + picture.getPictureName()
        );
        try (FileInputStream fileInputStream = new FileInputStream(pictureFile)) {
            return Files.readAllBytes(pictureFile.toPath());
        } catch (IOException e) {
            logger.error(String.format("Couldn't load file with path name:%s", pictureFile.getPath()), e);
            throw new LoadPictureException();
        }
    }

    public void removePictureFromFileSystem(Picture picture) {
        File pictureFile = new File(
                System.getProperties().getProperty("user.home")
                        + imageStoragePath
                        + picture.getUrl()
                        + picture.getPictureName()
        );
        try {
            Files.delete(pictureFile.toPath());
        } catch (Exception e) {
            logger.error(String.format("Couldn't delete file with path name:%s", pictureFile.getPath()), e);
            throw new RemovePictureException();
        }
    }

    public static String produceHashPictureDirectoryFilename(String fileName) {
        int hash = fileName.hashCode();
        int mask = 255;
        int firstDir = hash & mask;
        int secondDir = (hash >> 8) & mask;
        return File.separator +
                String.format("%03d", firstDir) +
                File.separator +
                String.format("%03d", secondDir) +
                File.separator;
    }
}
