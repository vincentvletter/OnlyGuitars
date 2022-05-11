package com.example.OnlyGuitars.service;

import com.example.OnlyGuitars.dto.GuitarInputDto;
import com.example.OnlyGuitars.dto.GuitarOutputDto;
import com.example.OnlyGuitars.model.Guitar;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GuitarService {

    public void createGuitar(MultipartFile image, String brand, String model);
    public GuitarOutputDto getGuitar(Long id);
    public List<GuitarOutputDto> getAllGuitars();
    public void deleteGuitar(Long id);
    public byte[] getImage(Long id);
    public Guitar toGuitar(GuitarInputDto guitarInputDto);
    public GuitarOutputDto getOneGuitarDto(Guitar guitar);
    public GuitarOutputDto fromGuitar(Guitar guitar);
    public GuitarOutputDto fromGuitarImage(Guitar guitar);
}
