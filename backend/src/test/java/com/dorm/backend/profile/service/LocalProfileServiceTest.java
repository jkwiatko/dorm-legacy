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
import java.util.List;
import java.util.stream.Stream;

import static com.dorm.backend.shared.data.enums.Inclination.EARLY_BIRD;
import static com.dorm.backend.shared.data.enums.Inclination.VEGAN;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LocalProfileServiceTest {

    private static final List<String> INTERESTS = Arrays.asList("Malowanie", "Czytanie");
    private static final List<Inclination> INCLINATIONS = Arrays.asList(VEGAN, EARLY_BIRD);
    private static final PictureDTO PICTURE = new PictureDTO("!@#", "user img name", 1);
    private static final String LOCAL_PICTURE_URL = "classpath:test.jpg";
    private static final String EXPECTED_PICTURE_HASH = "hash";
    private static final User EXPECTED_USER = getExpectedUser();
    public static final long USER_ID = 1L;

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

    private static User getExpectedUser() {
        User user = new User();
        user.setInterests(INTERESTS);
        user.setInclinations(INCLINATIONS);
        user.setProfilePictures(new ArrayList<>(localPictures(EXPECTED_PICTURE_HASH)));
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

        return singletonList(localPicture);
    }

    @Test
    public void shouldReplaceAllInterestsInclinationsAndPictures() {
        //given
        ProfileDTO updatedProfile = getUpdatedProfileDto();
        mockExternalServicesResponse();

        //when
        testedClass.editProfile(updatedProfile);

        //then
        verify(userService, times(1)).updateUser(userCaptor.capture());
        assertEquals(EXPECTED_USER.getInterests(), userCaptor.getValue().getInterests());
        assertEquals(EXPECTED_USER.getInclinations(), userCaptor.getValue().getInclinations());
        assertUpdatedPicture(EXPECTED_USER.getProfilePictures(), userCaptor.getValue().getProfilePictures());
    }

    private static ProfileDTO getUpdatedProfileDto() {
        ProfileDTO dto = new ProfileDTO();
        List<String> interests = new ArrayList<>(INTERESTS);
        List<String> inclinations = INCLINATIONS.stream().map(Inclination::getReadableText).collect(toList());
        List<PictureDTO> pictures = new ArrayList<>(singletonList(PICTURE));
        dto.setInterests(interests);
        dto.setInclinations(inclinations);
        dto.setProfilePictures(pictures);
        return dto;
    }

    private void mockExternalServicesResponse() {
        when(userService.getCurrentAuthenticatedUser()).thenReturn(getInitialUser());
        when(localPictureService.mapToLocalPictures(anyList())).thenReturn(localPictures(EXPECTED_PICTURE_HASH));
    }

    private static User getInitialUser() {
        User user = new User();
        user.setInterests(Stream.of("Yoga", "Filmy").collect(toList()));
        user.setInclinations(Stream.of(VEGAN, EARLY_BIRD).collect(toList()));
        user.setProfilePictures(new ArrayList<>(localPictures("previous hash")));
        return user;
    }

    private static void assertUpdatedPicture(List<LocalPictureEntity> expected, List<LocalPictureEntity> current) {
        assertEquals(expected.get(0).getPictureName(), current.get(0).getPictureName());
    }

    @Test
    public void shouldGetUserProfileById() {
        //given
        Long userId = USER_ID;
        mockDbResponse();

        //when
        testedClass.getUserProfile(userId);

        //then
        verify(userService, times(1)).getUser(eq(USER_ID));
        verify(modelMapper, times(1)).map(eq(EXPECTED_USER), eq(ProfileDTO.class));
    }

    private void mockDbResponse() {
        when(userService.getUser(any())).thenReturn(EXPECTED_USER);
    }



}