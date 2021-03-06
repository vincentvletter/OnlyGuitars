package com.example.OnlyGuitars.service;

import com.example.OnlyGuitars.dto.*;
import com.example.OnlyGuitars.exceptions.BadRequestException;
import com.example.OnlyGuitars.exceptions.RecordNotFoundException;
import com.example.OnlyGuitars.model.Guitar;
import com.example.OnlyGuitars.repository.GuitarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GuitarServiceImpl implements GuitarService {

    @Autowired
    GuitarRepository guitarRepository;
    @Autowired
    ReviewServiceImpl reviewService;
    @Autowired
    ProfileServiceImpl profileService;


    @Transactional
    public void createGuitar(MultipartFile image, String brand, String model) {
        GuitarInputDto guitarInputDto = new GuitarInputDto();

        if (guitarRepository.findByModel(model) == null) {
            try {
                if (image != null) {
                    guitarInputDto.setImage(image.getBytes());
                }
                guitarInputDto.setBrand(brand);
                guitarInputDto.setModel(model);

                Guitar guitar = toGuitar(guitarInputDto);
                guitarRepository.save(guitar);
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        else {
            throw new BadRequestException();
        }
    }

    public GuitarOutputDto getGuitar(Long id) {
        if (guitarRepository.findById(id).isPresent()) {

            Guitar guitar = guitarRepository.findById(id).get();

            return getOneGuitarDto(guitar);
        }
        else {
            throw new RecordNotFoundException("guitar not found");
        }
    }

    public List<GuitarOutputDto> getAllGuitars() {

        List<Guitar> allGuitars = guitarRepository.findAll();
        List<GuitarOutputDto> allGuitarOutputDtos = new ArrayList<>();

        allGuitars.forEach(guitar -> allGuitarOutputDtos.add(fromGuitar(guitar)));

        return allGuitarOutputDtos;
    }

    @Transactional
    public void deleteGuitar(Long id) {
        if (guitarRepository.findById(id).isPresent()) {

            Guitar guitar = guitarRepository.findById(id).get();

            guitar.getReviews().forEach(review -> reviewService.deleteReview(review.getId()));
            guitar.getProfiles().forEach(profile -> profileService.removeGuitar(profile.getId(), guitar));

            guitarRepository.delete(guitar);
        }
        else {
            throw new RecordNotFoundException("guitar not found");
        }
    }

    public byte[] getImage(Long id) {
        if (guitarRepository.findById(id).isPresent()) {

            Guitar guitar = guitarRepository.findById(id).get();

            GuitarOutputDto guitarOutputDto = fromGuitarImage(guitar);

            return guitarOutputDto.image;
        }
        else {
            throw new RecordNotFoundException("guitar not found");
        }
    }

    public Guitar toGuitar(GuitarInputDto guitarInputDto) {
        Guitar guitar = new Guitar();

        guitar.setBrand(guitarInputDto.getBrand());
        guitar.setModel(guitarInputDto.getModel());
        guitar.setImage(guitarInputDto.getImage());

        return guitar;
    }

    public GuitarOutputDto getOneGuitarDto(Guitar guitar) {
        GuitarOutputDto guitarOutputDto = new GuitarOutputDto();

        List<ReviewOutputDto> reviewOutputDtoList = new ArrayList<>();

        guitarOutputDto.id = guitar.getId();
        guitarOutputDto.brand = guitar.getBrand();
        guitarOutputDto.model = guitar.getModel();
        guitarOutputDto.imageApi = "http://localhost:8080/guitars/" + guitar.getId() + "/image";
        guitarOutputDto.timeStamp = guitar.getTimeStamp();
        guitarOutputDto.reviewListSize = guitar.getReviews().size();
        guitarOutputDto.profileLikes = guitar.getProfiles().size();

        guitar.getReviews().forEach(review -> reviewOutputDtoList.add(reviewService.fromReview(review)));
        guitarOutputDto.setReviewList(reviewOutputDtoList);

        return guitarOutputDto;
    }

    public GuitarOutputDto fromGuitar(Guitar guitar) {
        GuitarOutputDto guitarOutputDto = new GuitarOutputDto();

        guitarOutputDto.id = guitar.getId();
        guitarOutputDto.brand = guitar.getBrand();
        guitarOutputDto.model = guitar.getModel();
        guitarOutputDto.profileLikes = guitar.getProfiles().size();
        guitarOutputDto.reviewListSize = guitar.getReviews().size();

        return guitarOutputDto;
    }

    public GuitarOutputDto fromGuitarImage(Guitar guitar) {

        GuitarOutputDto guitarOutputDto = new GuitarOutputDto();
        guitarOutputDto.image = guitar.getImage();

        return guitarOutputDto;
    }
}

