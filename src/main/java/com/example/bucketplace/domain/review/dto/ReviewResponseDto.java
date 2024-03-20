package com.example.bucketplace.domain.review.dto;

import com.example.bucketplace.domain.review.entity.Review;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

public class ReviewResponseDto {

    @Getter
    public static class CreateReviewResponseDto {

        private final String nickname;
        private final String contents;
        private final int rating;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime createdAt;

        public CreateReviewResponseDto(Review review) {
            this.nickname = review.getMember().getNickname();
            this.contents = review.getContents();
            this.rating = review.getRating();
            this.createdAt = review.getCreatedAt();
        }
    }

    @Getter
    public static class UpdateReviewResponseDto {

        private final String nickname;
        private final String contents;
        private final int rating;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime createdAt;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime modifiedAt;

        public UpdateReviewResponseDto(Review review) {
            this.nickname = review.getMember().getNickname();
            this.contents = review.getContents();
            this.rating = review.getRating();
            this.createdAt = review.getCreatedAt();
            this.modifiedAt = review.getModifiedAt();
        }
    }

}
