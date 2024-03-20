package com.example.bucketplace.domain.review.dto;

import com.example.bucketplace.domain.member.entity.Member;
import com.example.bucketplace.domain.product.entity.Product;
import com.example.bucketplace.domain.review.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

public class ReviewRequestDto {

    @Getter
    public static class CreateReviewRequestDto {

        @Schema(description = "리뷰", example = "너무 예뻐요.")
        @NotBlank(message = "리뷰를 입력해주세요.")
        private String contents;

        @Schema(description = "별점", example = "5")
        @PositiveOrZero(message = "별점을 입력해주세요.")
        private int rating;

        public Review toEntity(Member member, Product product) {
            return Review.builder()
                    .contents(this.contents)
                    .rating(this.rating)
                    .member(member)
                    .product(product)
                    .build();
        }

    }

    @Getter
    public static class UpdateReviewRequestDto {

        @Schema(description = "리뷰", example = "너무너무너무 예뻐요.")
        @NotBlank(message = "리뷰를 입력해주세요.")
        private String contents;

        @Schema(description = "별점", example = "5")
        @PositiveOrZero(message = "별점을 입력해주세요.")
        private int rating;

        public Review toEntity(Member member, Product product) {
            return Review.builder()
                    .contents(this.contents)
                    .rating(this.rating)
                    .member(member)
                    .product(product)
                    .build();
        }

    }
}
