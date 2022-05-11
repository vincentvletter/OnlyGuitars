package com.example.OnlyGuitars.service;

import com.example.OnlyGuitars.OnlyGuitarsApplication;
import com.example.OnlyGuitars.dto.RequestInputDto;
import com.example.OnlyGuitars.dto.RequestOutputDto;
import com.example.OnlyGuitars.model.Request;
import com.example.OnlyGuitars.repository.RequestRepository;
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

@SpringBootTest
@ContextConfiguration(classes={OnlyGuitarsApplication.class})
class RequestServiceImplTest {

    @Autowired
    private RequestService requestService;

    @MockBean
    private RequestRepository requestRepository;

    @Mock
    Request request;

    @Test
    public void shouldReturnRequestOutputDto() {
        request = new Request();
        request.setId(1L);
        request.setBrand("fender");
        request.setModel("telecaster");

        RequestOutputDto requestOutputDto = requestService.fromRequest(request);

        String expected = "telecaster";

        assertEquals(expected, requestOutputDto.model);
    }

    @Test
    public void shouldReturnRequest() {
        RequestInputDto requestInputDto = new RequestInputDto();
        requestInputDto.brand = "gibson";
        requestInputDto.model = "les paul";

        request = requestService.toRequest(requestInputDto);

        String expected = "les paul";

        assertEquals(expected,request.getModel());
    }

    @Test
    public void shouldReturnRequestOutputDtoList() {
        Request requestOne = new Request();
        Request requestTwo = new Request();
        Request requestThree = new Request();

        List<Request> requestList = new ArrayList<>();
        requestList.add(requestOne);
        requestList.add(requestTwo);
        requestList.add(requestThree);

        Mockito
                .when(requestRepository.findAll())
                .thenReturn(requestList);

        List<RequestOutputDto> requestOutputDtoList = requestService.getAllRequests();

        int expected = 3;

        assertEquals(expected, requestOutputDtoList.size());
    }
}