package com.example.bucketplace.domain.review.repository;

import com.example.bucketplace.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
