package com.example.OnlyGuitars.service;

import com.example.OnlyGuitars.dto.*;
import com.example.OnlyGuitars.exceptions.BadRequestException;
import com.example.OnlyGuitars.exceptions.RecordNotFoundException;
import com.example.OnlyGuitars.model.Authority;
import com.example.OnlyGuitars.model.Guitar;
import com.example.OnlyGuitars.model.Profile;
import com.example.OnlyGuitars.model.Review;
import com.example.OnlyGuitars.repository.AuthorityRepository;
import com.example.OnlyGuitars.repository.GuitarRepository;
import com.example.OnlyGuitars.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
public class ProfileServiceImpl implements ProfileService {



    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    GuitarRepository guitarRepository;
    @Autowired
    AuthorityService authorityService;
    @Autowired
    AuthorityRepository authorityRepository;
    @Autowired
    GuitarServiceImpl guitarService;
    @Autowired
    ReviewServiceImpl reviewService;
    @Autowired
    PasswordEncoder passwordEncoder;


    public void createProfile(ProfileInputDto profileInputDto) {
        if (profileRepository.findByUsername(profileInputDto.username) == null) {
            profileInputDto.setPassword(passwordEncoder.encode(profileInputDto.getPassword()));
            Profile profile = toProfile(profileInputDto);
            profileRepository.save(profile);
        } else {
            throw new BadRequestException("Username already exist");
        }
    }

    public ProfileOutputDto updateProfile(ProfileInputDto profileInputDto, String username) {
        if (profileRepository.findByUsername(profileInputDto.getUsername()) == null) {
            Profile profile = profileRepository.findByUsername(username);
            if (!profileInputDto.username.equals(profile.getUsername())) {
                profile.setUsername(profileInputDto.getUsername());
            }
            profile.setPassword(passwordEncoder.encode(profileInputDto.getPassword()));
            profileRepository.save(profile);
            return fromProfile(profile);
        } else {
            throw new BadRequestException("Username already exist");
        }
    }

    @Transactional
    public ProfileOutputDto getProfile(String username) {
        Profile profile = profileRepository.findByUsername(username);
        return fromProfile(profile);
    }

    public void deleteProfile(String username) {
        Profile profile = profileRepository.findByUsername(username);
        if (profileRepository.findByUsername(profile.getUsername()) != null) {
            profileRepository.deleteById(profile.getId());
        } else {
            throw new RecordNotFoundException("profile not found");
        }
    }

    @Transactional
    public void addGuitarToProfile(GuitarInputDto guitarInputDto, String username) {
        Profile profile = profileRepository.findByUsername(username);
        Guitar guitar = guitarRepository.findById(guitarInputDto.getId()).get();
        if (!profile.getGuitars().contains(guitar)) {
            profile.getGuitars().add(guitar);
            profileRepository.save(profile);
        } else {
            throw new BadRequestException();
        }
    }

    @Transactional
    public void removeGuitarFromList(GuitarInputDto guitarInputDto, String username) {
        Profile profile = profileRepository.findByUsername(username);
        Guitar guitar = guitarRepository.findById(guitarInputDto.getId()).get();
        if (profile.getGuitars().contains(guitar)) {
            profile.getGuitars().remove(guitar);
            profileRepository.save(profile);
        } else {
            throw new BadRequestException();
        }
    }

    public void removeGuitar(Long id, Guitar guitar) {
        Profile profile = profileRepository.findById(id).get();
        if (profile.getGuitars().contains(guitar)) {
            profile.getGuitars().remove(guitar);
        } else {
            throw new BadRequestException();
        }
    }

    public void writeReview(Long id, ReviewInputDto reviewInputDto, String username) {
        Profile profile = profileRepository.findByUsername(username);
        Guitar guitar = guitarRepository.findById(id).get();
        reviewInputDto.setProfile(profile);
        reviewInputDto.setGuitar(guitar);
        Review review = reviewService.addReview(reviewInputDto);
        profile.getReviews().add(review);
        guitar.getReviews().add(review);
        profileRepository.save(profile);
        guitarRepository.save(guitar);
    }

    public Profile toProfile(ProfileInputDto profileInputDto) {
        Authority authority = new Authority(profileInputDto.getUsername(), "USER");

        Profile profile = new Profile();

        profile.setUsername(profileInputDto.getUsername());
        profile.setPassword(profileInputDto.getPassword());
        profile.setEnabled(1);
        profile.setAuthority(authority);

        authority.setProfile(profile);

        return profile;
    }

    public ProfileOutputDto fromProfile(Profile profile) {
        ProfileOutputDto profileOutputDto = new ProfileOutputDto();
        List<GuitarOutputDto> guitarOutputDtoList = new ArrayList<>();
        profileOutputDto.id = profile.getId();
        profileOutputDto.username = profile.getUsername();
        profileOutputDto.enabled = profile.getEnabled();
        profileOutputDto.timeStamp = profile.getTimeStamp();
        profileOutputDto.authority = authorityService.fromAuthority(profile.getAuthority());
        profileOutputDto.role = profileOutputDto.authority.getAutority();

        profile.getGuitars().forEach(guitar -> guitarOutputDtoList.add(guitarService.fromGuitar(guitar)));

        profileOutputDto.setGuitarList(guitarOutputDtoList);
        return profileOutputDto;
    }
}


