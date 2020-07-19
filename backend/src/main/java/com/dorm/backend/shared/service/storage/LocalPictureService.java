package com.dorm.backend.shared.service.storage;

import com.dorm.backend.shared.data.dto.PictureDTO;
import com.dorm.backend.shared.data.entity.picture.LocalPicture;
import com.dorm.backend.shared.data.repo.PictureRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class LocalPictureService {

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
}
