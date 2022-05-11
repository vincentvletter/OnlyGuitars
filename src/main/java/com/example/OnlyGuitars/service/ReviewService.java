package com.example.OnlyGuitars.service;

import com.example.OnlyGuitars.dto.ReviewInputDto;
import com.example.OnlyGuitars.dto.ReviewOutputDto;
import com.example.OnlyGuitars.model.Review;

public interface ReviewService {

    public Review addReview(ReviewInputDto reviewInputDto);
    public void deleteReview(Long id);
    public Review toReview(ReviewInputDto reviewInputDto);
    public ReviewOutputDto fromReview(Review review);
}
