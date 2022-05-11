package com.example.OnlyGuitars.service;

import com.example.OnlyGuitars.dto.RequestInputDto;
import com.example.OnlyGuitars.dto.RequestOutputDto;
import com.example.OnlyGuitars.model.Request;

import java.util.List;

public interface RequestService {

    public void createRequest(RequestInputDto requestInputDto);
    public List<RequestOutputDto> getAllRequests();
    public void deleteRequest(Long id);
    public RequestOutputDto fromRequest(Request request);
    public Request toRequest(RequestInputDto requestInputDto);
}
