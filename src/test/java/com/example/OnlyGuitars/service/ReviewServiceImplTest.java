package com.example.OnlyGuitars.service;

import com.example.OnlyGuitars.OnlyGuitarsApplication;
import com.example.OnlyGuitars.dto.ReviewOutputDto;
import com.example.OnlyGuitars.model.Review;
import com.example.OnlyGuitars.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes={OnlyGuitarsApplication.class})
class ReviewServiceImplTest {

    @Autowired
    private ReviewService reviewService;

    @MockBean
    private ReviewRepository reviewRepository;

    @Mock
    Review review;

    @Test
    public void shouldReturnReviewOutputDto() {
        review = new Review();
        review.setId(1L);
        review.setTitle("test");
        review.setDetails("Dit is een test.");

        ReviewOutputDto reviewOutputDto = reviewService.fromReview(review);

        String expected = "test";

        assertEquals(expected, reviewOutputDto.title);
    }
}