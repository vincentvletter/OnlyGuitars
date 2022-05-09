package com.example.OnlyGuitars.service;

import com.example.OnlyGuitars.OnlyGuitarsApplication;
import com.example.OnlyGuitars.dto.GuitarInputDto;
import com.example.OnlyGuitars.dto.ProfileInputDto;
import com.example.OnlyGuitars.dto.ProfileOutputDto;
import com.example.OnlyGuitars.dto.ReviewInputDto;
import com.example.OnlyGuitars.model.Authority;
import com.example.OnlyGuitars.model.Guitar;
import com.example.OnlyGuitars.model.Profile;
import com.example.OnlyGuitars.model.Review;
import com.example.OnlyGuitars.repository.GuitarRepository;
import com.example.OnlyGuitars.repository.ProfileRepository;
import com.example.OnlyGuitars.repository.ReviewRepository;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes={OnlyGuitarsApplication.class})
class ProfileServiceImplTest {

    @Autowired
    private ProfileService profileService;

    @MockBean
    private ProfileRepository profileRepository;

    @MockBean
    private GuitarRepository guitarRepository;

    @MockBean
    ReviewRepository reviewRepository;

    @Mock
    Profile profile;

    @Mock
    Guitar guitar;

    @Mock
    Review review;

    @Test
    public void shouldReturnProfile() {
        ProfileInputDto profileInputDto = new ProfileInputDto();
        profileInputDto.username = "vincent";
        profileInputDto.password = "$2a$10$Z2cPoT34PCz9zqYmw3.Dt.UwcRoGMJYZMovkkUyyxkDASwnX0I8bq";
        profileInputDto.id = 2L;


        Profile profile = profileService.toProfile(profileInputDto);

        String expected = "vincent";

        assertEquals(expected, profile.getUsername());
    }

    @Test
    public void shouldReturnProfileOutputDto() {
        Authority authority = new Authority();
        authority.setUsername("vincent");
        authority.setAuthority("ADMIN");

        Profile profile = new Profile();
        profile.setId(4L);
        profile.setUsername("vincent");
        profile.setAuthority(authority);
        profile.setEnabled(1);

        ProfileOutputDto profileOutputDto = profileService.fromProfile(profile);

        String expected = "ADMIN";

        assertEquals(expected, profileOutputDto.role);
    }

    @Test
    public void shouldGetProfile() {
        Authority authority = new Authority();
        authority.setUsername("vincent");
        authority.setAuthority("ADMIN");

        profile = new Profile();
        profile.setUsername("vincent");
        profile.setPassword("$2a$10$Z2cPoT34PCz9zqYmw3.Dt.UwcRoGMJYZMovkkUyyxkDASwnX0I8bq");
        profile.setAuthority(authority);

        Mockito
                .when(profileRepository.findByUsername(profile.getUsername()))
                .thenReturn(profile);

        String name = "vincent";
        String expected = "vincent";

        ProfileOutputDto found = profileService.getProfile(name);

        assertEquals(expected, found.getUsername());
    }


    @Test
    public void shouldUpdateProfile() {
        Authority authority = new Authority();
        authority.setUsername("vincent");
        authority.setAuthority("ADMIN");

        profile = new Profile();
        profile.setUsername("vincent");
        profile.setPassword("$2a$10$Z2cPoT34PCz9zqYmw3.Dt.UwcRoGMJYZMovkkUyyxkDASwnX0I8bq");
        profile.setAuthority(authority);
        profile.setId(1L);

        ProfileInputDto profileInputDto = new ProfileInputDto();
        profileInputDto.username = "vinny";
        profileInputDto.setPassword("$2a$10$Z2cPoT34PCz9zqYmw3.Dt.UwcRoGMJYZMovkkUyyxkDASwnX0I8bq");

        Mockito
                .when(profileRepository.findByUsername(profile.getUsername()))
                .thenReturn(profile);


        String name = "vincent";
        String expected = "vinny";

        ProfileOutputDto found = profileService.updateProfile(profileInputDto, name);

        assertEquals(expected, found.getUsername());
    }


    @Test
    public void shouldAddGuitarToProfile() {
        Authority authority = new Authority();
        authority.setUsername("vincent");
        authority.setAuthority("ADMIN");

        profile = new Profile();
        profile.setUsername("vincent");
        profile.setAuthority(authority);

        ProfileInputDto profileInputDto = new ProfileInputDto();
        profileInputDto.setUsername("vincent");

        guitar = new Guitar();
        guitar.setId(6L);
        guitar.setBrand("fender");
        guitar.setModel("stratocaster");

        GuitarInputDto guitarInputDto = new GuitarInputDto();
        guitarInputDto.setId(6L);
        guitarInputDto.setBrand("fender");
        guitarInputDto.setBrand("stratocaster");

        Mockito
                .when(profileRepository.findByUsername(profile.getUsername()))
                .thenReturn(profile);
        Mockito
                .when(guitarRepository.findById(guitar.getId()))
                .thenReturn(java.util.Optional.of(guitar));

        profileService.addGuitarToProfile(guitarInputDto, profile.getUsername());

        ProfileOutputDto found = profileService.getProfile(profile.getUsername());

        int expected = 1;

        assertEquals(expected, found.guitarList.size());
    }

    @Test
    public void shouldRemoveGuitarFromList() {
        guitar = new Guitar();
        guitar.setId(6L);
        guitar.setBrand("fender");
        guitar.setModel("stratocaster");

        profile = new Profile();
        profile.setUsername("vincent");
        profile.getGuitars().add(guitar);

        GuitarInputDto guitarInputDto = new GuitarInputDto();
        guitarInputDto.setId(6L);

        Mockito
                .when(profileRepository.findByUsername(profile.getUsername()))
                .thenReturn(profile);
        Mockito
                .when(guitarRepository.findById(guitar.getId()))
                .thenReturn(java.util.Optional.of(guitar));

        profileService.removeGuitarFromList(guitarInputDto, profile.getUsername());

        int expected = 0;

        assertEquals(expected, profile.getGuitars().size());
    }

    @Test
    public void shouldAddReview() {
        guitar = new Guitar();
        guitar.setId(6L);
        guitar.setBrand("fender");
        guitar.setModel("stratocaster");

        profile = new Profile();
        profile.setUsername("vincent");

        review = new Review();

        ReviewInputDto reviewInputDto = new ReviewInputDto();
        reviewInputDto.title = "Test";
        reviewInputDto.details = "Dit is een test.";

        Mockito
                .when(profileRepository.findByUsername(profile.getUsername()))
                .thenReturn(profile);
        Mockito
                .when(guitarRepository.findById(guitar.getId()))
                .thenReturn(java.util.Optional.of(guitar));
        Mockito
                .when(reviewRepository.save(review))
                .thenReturn(review);

        profileService.writeReview(guitar.getId(), reviewInputDto, profile.getUsername());

        String expected = "Test";

        assertEquals(expected, profile.getReviews().get(0).getTitle());
    }
}