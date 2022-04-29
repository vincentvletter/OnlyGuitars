package com.example.OnlyGuitars.repository;

import com.example.OnlyGuitars.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
