package com.example.bucketplace.domain.review.dto;

import com.example.bucketplace.domain.review.entity.Review;
import lombok.Getter;

public class ReviewResponseDto {

    @Getter
    public static class CreateReviewResponseDto {

        private String contents;
        private int rating;

        public CreateReviewResponseDto(Review review) {
            this.contents = review.getContents();
            this.rating = review.getRating();
        }
    }
}
