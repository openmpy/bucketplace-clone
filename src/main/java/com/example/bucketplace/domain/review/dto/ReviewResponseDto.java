package com.example.bucketplace.domain.review.dto;

import com.example.bucketplace.domain.member.entity.Member;
import com.example.bucketplace.domain.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

public class ReviewResponseDto {

    @Getter
    public static class CreateReviewResponseDto {

        private final Member nickname;
        private final String contents;
        private final int rating;
        private final LocalDateTime createdAt;

        public CreateReviewResponseDto(Review review) {
            this.nickname = getNickname();
            this.contents = review.getContents();
            this.rating = review.getRating();
            this.createdAt = review.getCreatedAt();
        }
    }

}
