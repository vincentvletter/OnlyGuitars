package com.example.OnlyGuitars.service;

import com.example.OnlyGuitars.dto.ReviewInputDto;
import com.example.OnlyGuitars.dto.ReviewOutputDto;
import com.example.OnlyGuitars.model.Review;
import com.example.OnlyGuitars.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;


    public Review addReview(ReviewInputDto reviewInputDto) {
        Review review = toReview(reviewInputDto);
        reviewRepository.save(review);
        return review;
    }

    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id).get();
        reviewRepository.delete(review);
    }

    public Review toReview(ReviewInputDto reviewInputDto) {
        Review review = new Review();
        review.setTitle(reviewInputDto.getTitle());
        review.setDetails(reviewInputDto.getDetails());
        review.setGuitar(reviewInputDto.getGuitar());
        review.setProfile(reviewInputDto.getProfile());
        return review;
    }

    public ReviewOutputDto fromReview(Review review) {

        ReviewOutputDto reviewOutputDto = new ReviewOutputDto();

        reviewOutputDto.id = review.getId();
        reviewOutputDto.title = review.getTitle();
        reviewOutputDto.details = review.getDetails();
        reviewOutputDto.timeStamp = review.getTimeStamp();
        reviewOutputDto.username = review.getProfile().getUsername();

        return reviewOutputDto;
    }
}
