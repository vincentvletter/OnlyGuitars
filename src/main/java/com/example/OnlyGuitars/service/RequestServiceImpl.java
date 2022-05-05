package com.example.OnlyGuitars.service;

import com.example.OnlyGuitars.dto.RequestInputDto;
import com.example.OnlyGuitars.dto.RequestOutputDto;
import com.example.OnlyGuitars.exceptions.RecordNotFoundException;
import com.example.OnlyGuitars.model.Request;
import com.example.OnlyGuitars.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService{

    @Autowired
    RequestRepository requestRepository;

    public void createRequest(RequestInputDto requestInputDto) {
        Request request = toRequest(requestInputDto);
        requestRepository.save(request);
    }

    public List<RequestOutputDto> getAllRequests() {
        List<Request> allRequests = requestRepository.findAll();
        List<RequestOutputDto> allRequestOutputDtos = new ArrayList<>();

        allRequests.forEach(request -> allRequestOutputDtos.add(fromRequest(request)));
        return allRequestOutputDtos;
    }

    public void deleteRequest(Long id) {
        if(requestRepository.existsById(id)) {
            requestRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("id not found");
        }
    }

    public RequestOutputDto fromRequest(Request request) {
        RequestOutputDto requestOutputDto = new RequestOutputDto();
        requestOutputDto.id = request.getId();
        requestOutputDto.brand = request.getBrand();
        requestOutputDto.model = request.getModel();
        requestOutputDto.timeStamp = request.getTimeStamp();
        return requestOutputDto;
    }

    public Request toRequest(RequestInputDto requestInputDto) {
        Request request = new Request();
        request.setBrand(requestInputDto.getBrand());
        request.setModel(requestInputDto.getModel());
        return request;
    }
}
