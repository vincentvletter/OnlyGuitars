package com.example.OnlyGuitars.service;

import com.example.OnlyGuitars.dto.*;
import com.example.OnlyGuitars.model.Guitar;
import com.example.OnlyGuitars.model.Profile;
import com.example.OnlyGuitars.model.Review;
import com.example.OnlyGuitars.repository.GuitarRepository;
import com.example.OnlyGuitars.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    GuitarServiceImpl guitarService;
    @Autowired
    ReviewServiceImpl reviewService;
    @Autowired
    PasswordEncoder passwordEncoder;


    public void createProfile(ProfileInputDto profileInputDto) {
        Profile profile = profileRepository.findByUsername(profileInputDto.username);
        if (profile == null) {
            profileInputDto.setPassword(passwordEncoder.encode(profileInputDto.getPassword()));
            profile = toProfile(profileInputDto);
            profileRepository.save(profile);
        } else {
            throw new RuntimeException();
        }
    }

    public void updateProfile(ProfileInputDto profileInputDto) {
        if (profileRepository.findByUsername(profileInputDto.getUsername()) == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            Profile profile = profileRepository.findByUsername(userDetails.getUsername());
            if (!profileInputDto.username.equals(profile.getUsername())) {
                profile.setUsername(profileInputDto.getUsername());
            }
            profile.setPassword(passwordEncoder.encode(profileInputDto.getPassword()));
            profileRepository.save(profile);
        } else {
            throw new RuntimeException();
        }
    }

    @Transactional
    public ProfileOutputDto getProfile() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        Profile profile = profileRepository.findByUsername(userDetails.getUsername());
        ProfileOutputDto profileOutputDto = fromProfile(profile);

        return profileOutputDto;
    }

    public void deleteProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        Profile profile = profileRepository.findByUsername(userDetails.getUsername());
        if (profileRepository.findByUsername(profile.getUsername()) != null) {
            profileRepository.deleteById(profile.getId());
        }
    }

    @Transactional
    public void addGuitarToProfile(GuitarInputDto guitarInputDto) {
        StatusOutput statusOutput = new StatusOutput();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        Profile profile = profileRepository.findByUsername(userDetails.getUsername());

        Guitar guitar = guitarRepository.findById(guitarInputDto.getId()).get();
        if (!profile.getGuitars().contains(guitar)) {
            profile.getGuitars().add(guitar);
            profileRepository.save(profile);
        } else {
            throw new RuntimeException();
        }
    }

    @Transactional
    public void removeGuitarFromList(GuitarInputDto guitarInputDto) {
        StatusOutput statusOutput = new StatusOutput();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        Profile profile = profileRepository.findByUsername(userDetails.getUsername());

        Guitar guitar = guitarRepository.findById(guitarInputDto.getId()).get();
        if (profile.getGuitars().contains(guitar)) {
            profile.getGuitars().remove(guitar);
            profileRepository.save(profile);
        } else {
            throw new RuntimeException();
        }
    }

    public void removeGuitar(Long id, Guitar guitar) {
        Profile profile = profileRepository.findById(id).get();
        if (profile.getGuitars().contains(guitar)) {
            profile.getGuitars().remove(guitar);
        }
    }

    public void writeReview(Long id, ReviewInputDto reviewInputDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        Profile profile = profileRepository.findByUsername(userDetails.getUsername());
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

        Profile profile = new Profile();

        profile.setUsername(profileInputDto.getUsername());
        profile.setPassword(profileInputDto.getPassword());
        profile.setRole(profileInputDto.getRole());
        profile.setEnabled(1);

        return profile;
    }

    public ProfileOutputDto fromProfile(Profile profile) {

        ProfileOutputDto profileOutputDto = new ProfileOutputDto();

        List<GuitarOutputDto> guitarOutputDtoList = new ArrayList<>();

        profileOutputDto.id = profile.getId();
        profileOutputDto.username = profile.getUsername();
        profileOutputDto.role = profile.getRole();
        profileOutputDto.enabled = profile.getEnabled();
        profileOutputDto.timeStamp = profile.getTimeStamp();

        profile.getGuitars().forEach(guitar -> guitarOutputDtoList.add(guitarService.fromGuitar(guitar)));

        profileOutputDto.setGuitarList(guitarOutputDtoList);
        return profileOutputDto;
    }
}


