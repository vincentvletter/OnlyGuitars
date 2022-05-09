package com.example.OnlyGuitars.service;

import com.example.OnlyGuitars.OnlyGuitarsApplication;
import com.example.OnlyGuitars.dto.GuitarInputDto;
import com.example.OnlyGuitars.dto.GuitarOutputDto;
import com.example.OnlyGuitars.model.Guitar;
import com.example.OnlyGuitars.model.Profile;
import com.example.OnlyGuitars.model.Review;
import com.example.OnlyGuitars.repository.GuitarRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@ContextConfiguration(classes={OnlyGuitarsApplication.class})
class GuitarServiceImplTest {

    @Autowired
    private GuitarService guitarService;

    @MockBean
    private GuitarRepository guitarRepository;

    @Mock
    Guitar guitar;


    @Test
    public void shouldReturnGuitar() {
        GuitarInputDto guitarInputDto = new GuitarInputDto();
        guitarInputDto.setId(1L);
        guitarInputDto.setBrand("gibson");
        guitarInputDto.setModel("les paul");

        guitar = guitarService.toGuitar(guitarInputDto);

        String expected = "les paul";

        assertEquals(expected, guitar.getModel());
    }

    @Test
    public void shouldReturnGuitarOutputDto() {
        guitar = new Guitar();
        guitar.setId(2L);
        guitar.setBrand("gretsch");
        guitar.setModel("es-399");

        GuitarOutputDto guitarOutputDto = guitarService.getOneGuitarDto(guitar);

        String expected = "gretsch";

        assertEquals(expected, guitarOutputDto.brand);
    }

    @Test
    public void shouldGetGuitar() {
        guitar = new Guitar();
        guitar.setId(3L);
        guitar.setBrand("ibanez");

        Mockito
                .when(guitarRepository.findById(guitar.getId()))
                .thenReturn(java.util.Optional.ofNullable(guitar));

        GuitarOutputDto guitarOutputDto = guitarService.getGuitar(3L);

        String expected = "ibanez";

        assertEquals(expected, guitarOutputDto.brand);
    }

    @Test
    public void shouldGetAllGuitars() {
        Guitar guitarOne = new Guitar();
        Guitar guitarTwo = new Guitar();
        Guitar guitarThree = new Guitar();
        List<Guitar> guitarList = new ArrayList<>();

        guitarList.add(guitarOne);
        guitarList.add(guitarTwo);
        guitarList.add(guitarThree);

        Mockito
                .when(guitarRepository.findAll())
                .thenReturn(guitarList);

        List<GuitarOutputDto> guitarOutputDtoList = guitarService.getAllGuitars();

        int expected = 3;

        assertEquals(expected, guitarOutputDtoList.size());
    }

    @Test
    public void shouldDeleteGuitar() {
        Review review = new Review();
        Profile profile = new Profile();

        guitar.setId(1L);
        guitar.getReviews().add(review);
        guitar.getProfiles().add(profile);

        Mockito
                .when(guitarRepository.findById(guitar.getId()))
                .thenReturn(java.util.Optional.ofNullable(guitar));

        doNothing()
                .when(guitarRepository).delete(guitar);

        guitarService.deleteGuitar(guitar.getId());

        verify(guitarRepository,times(1)).delete(guitar);
    }
}