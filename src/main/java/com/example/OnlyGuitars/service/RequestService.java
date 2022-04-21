package com.example.OnlyGuitars.service;

import com.example.OnlyGuitars.dto.RequestInputDto;
import com.example.OnlyGuitars.dto.RequestOutputDto;

import java.util.List;

public interface RequestService {
    public void createRequest(RequestInputDto requestInputDto);
    public List<RequestOutputDto> getAllRequests();
    public void deleteRequest(Long id);
}
