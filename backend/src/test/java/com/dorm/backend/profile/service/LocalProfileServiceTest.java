package com.dorm.backend.profile.service;

import com.dorm.backend.profile.dto.ProfileDTO;
import com.dorm.backend.profile.service.local.LocalProfileService;
import com.dorm.backend.shared.data.dto.PictureDTO;
import com.dorm.backend.shared.data.entity.User;
import com.dorm.backend.shared.data.entity.picture.LocalPicture;
import com.dorm.backend.shared.data.entity.picture.LocalPictureEntity;
import com.dorm.backend.shared.data.enums.Inclination;
import com.dorm.backend.shared.data.repo.search.ProfileSearchRepository;
import com.dorm.backend.shared.service.UserService;
import com.dorm.backend.shared.service.storage.local.LocalPictureService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.dorm.backend.shared.data.enums.Inclination.EARLY_BIRD;
import static com.dorm.backend.shared.data.enums.Inclination.VEGAN;

@RunWith(MockitoJUnitRunner.class)
public class LocalProfileServiceTest {

    private static final List<String> INTERESTS = Arrays.asList("Malowanie", "Czytanie");
    private static final List<Inclination> INCLINATIONS = Arrays.asList(VEGAN, EARLY_BIRD);
    private static final PictureDTO PICTURE = new PictureDTO("!@#", "dummy name", 1);
    private static final String LOCAL_PICTURE_URL = "classpath:test.jpg";
    public static final String EXPECTED_PICTURE_NAME = "expected";
    private static final User EXPECTED_USER = getExpectedUser();
    private static final User EDITED_USER = getEditedUser();
    private static final List<LocalPicture> LOCAL_PICTURES = localPictures(EXPECTED_PICTURE_NAME);

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private UserService userService;
    @Mock
    private LocalPictureService localPictureService;
    @Mock
    private ProfileSearchRepository profileSearchRepository;
    @Captor
    private ArgumentCaptor<User> userCaptor;

    @InjectMocks
    private LocalProfileService testedClass;

    @Test
    public void shouldReplaceAllInterestsInclinationsAndPictures() {
        //given
        Mockito.when(userService.getCurrentAuthenticatedUser()).thenReturn(EDITED_USER);
        Mockito.when(localPictureService.mapToLocalPictures(Mockito.anyList())).thenReturn(LOCAL_PICTURES);
        ProfileDTO profileDto = profileDto();

        //when
        testedClass.editProfile(profileDto);

        //then
        Mockito.verify(userService, Mockito.times(1)).updateUser(userCaptor.capture());
        Assert.assertEquals(EXPECTED_USER.getInterests(), userCaptor.getValue().getInterests());
        Assert.assertEquals(EXPECTED_USER.getInclinations(), userCaptor.getValue().getInclinations());
        assertPictures(EXPECTED_USER.getProfilePictures(), userCaptor.getValue().getProfilePictures());
    }

    private static void assertPictures(List<LocalPictureEntity> expected, List<LocalPictureEntity> current) {
        Assert.assertEquals(expected.get(0).getPictureName(), current.get(0).getPictureName());
    }

    private static ProfileDTO profileDto() {
        ProfileDTO dto = new ProfileDTO();
        List<String> interests = new ArrayList<>(INTERESTS);
        List<String> inclinations = INCLINATIONS.stream().map(Inclination::getReadableText).collect(Collectors.toList());
        List<PictureDTO> pictures = new ArrayList<>(Collections.singletonList(PICTURE));
        dto.setInterests(interests);
        dto.setInclinations(inclinations);
        dto.setProfilePictures(pictures);
        return dto;
    }

    private static User getEditedUser() {
        User user = new User();
        user.setInterests(Stream.of("Yoga", "Filmy").collect(Collectors.toList()));
        user.setInclinations(Stream.of(VEGAN, EARLY_BIRD).collect(Collectors.toList()));
        user.setProfilePictures(new ArrayList<>(localPictures("edited")));
        return user;
    }

    private static User getExpectedUser() {
        User user = new User();
        user.setInterests(INTERESTS);
        user.setInclinations(INCLINATIONS);
        user.setProfilePictures(new ArrayList<>(localPictures("expected")));
        return user;
    }

    private static List<LocalPicture> localPictures(String name) {
        LocalPicture localPicture = new LocalPicture();
        localPicture.setPictureName(name);
        try {
            localPicture.setPicture(Files.readAllBytes(ResourceUtils.getFile(LOCAL_PICTURE_URL).toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.singletonList(localPicture);
    }
}