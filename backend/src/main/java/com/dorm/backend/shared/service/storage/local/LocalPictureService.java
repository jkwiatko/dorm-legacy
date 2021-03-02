package com.dorm.backend.shared.service.storage.local;

import com.dorm.backend.shared.data.dto.PictureDTO;
import com.dorm.backend.shared.data.entity.picture.LocalPicture;
import com.dorm.backend.shared.data.entity.picture.LocalPictureEntity;
import com.dorm.backend.shared.data.repo.PictureRepository;
import com.dorm.backend.shared.error.exc.LoadPictureException;
import com.dorm.backend.shared.error.exc.PersistFileException;
import com.dorm.backend.shared.error.exc.RemovePictureException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LocalPictureService {

    private static final String imageStoragePath = "/dorm/image_storage";

    ModelMapper modelMapper;
    PictureRepository pictureRepository;

    public LocalPictureService(ModelMapper modelMapper, PictureRepository pictureRepository) {
        this.modelMapper = modelMapper;
        this.pictureRepository = pictureRepository;
    }

    public List<LocalPicture> mapToLocalPictures(List<PictureDTO> dtoList) {
        Random random = new Random();
        String lastId = Long.toString(pictureRepository.getLastIdAsString().orElse(0L) + 1);
        String lastDigit = lastId.substring(lastId.length() - 1);
        return dtoList
                .stream()
                .map(dto -> modelMapper.map(dto, LocalPicture.class))
                .peek(localPicture -> localPicture.setPictureName(random.nextInt(100000) + lastDigit))
                .collect(Collectors.toList());
    }

    public void savePicture(LocalPicture picture) {
        try {
            persistPictureToFileSystem(picture);
        } catch (IOException e) {
            log.error("IOException on img saving procedure -> url: " + picture.getUrl(), e);
            throw new PersistFileException();
        }
    }

    public static byte[] loadPictureFromFileSystem(LocalPictureEntity picture) {
        File pictureFile = new File(
                System.getProperties().getProperty("user.home")
                        + imageStoragePath
                        + picture.getUrl()
                        + picture.getPictureName());
        try {
            return Files.readAllBytes(pictureFile.toPath());
        } catch (IOException e) {
            log.error(String.format("Couldn't load file with path name:%s", pictureFile.getPath()), e);
            throw new LoadPictureException();
        }
    }

    public void deletePicture(LocalPictureEntity picture) {
        removePictureFromFileSystem(picture);
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

    private static void persistPictureToFileSystem(LocalPicture picture) throws IOException {
        File pictureFile = new File(
                System.getProperties().getProperty("user.home")
                        + imageStoragePath
                        + picture.getUrl()
                        + picture.getPictureName());

        try (InputStream inputStream = new ByteArrayInputStream(picture.getPicture())) {
            if (!pictureFile.getParentFile().exists() &&
                    !pictureFile.getParentFile().mkdirs()) {
                throw new IOException();
            }
            BufferedImage bImage = ImageIO.read(inputStream);
            ImageIO.write(bImage, "jpg", pictureFile);
        }
    }

    private static void removePictureFromFileSystem(LocalPictureEntity picture) {
        File pictureFile = new File(
                System.getProperties().getProperty("user.home")
                        + imageStoragePath
                        + picture.getUrl()
                        + picture.getPictureName());
        try {
            Files.delete(pictureFile.toPath());
        } catch (Exception e) {
            log.error(String.format("Couldn't delete file with path name:%s", pictureFile.getPath()), e);
            throw new RemovePictureException();
        }
    }
}
