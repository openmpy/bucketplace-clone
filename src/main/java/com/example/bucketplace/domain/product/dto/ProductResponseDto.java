package com.example.bucketplace.domain.product.dto;

import com.example.bucketplace.domain.product.entity.Product;
import com.example.bucketplace.domain.review.dto.ReviewResponseDto.GetReviewResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class ProductResponseDto {
    @Getter
    public static class GetProductResponseDto {
        private final Long productId;
        private final String brand;
        private final String name;
        private final double discount;
        private final long price;
        private final String imageUrl;
        private final Boolean isFreeDelivery;
        private final Boolean isSpecialPrice;
        private final Boolean isBookmarked;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime createdAt;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime modifiedAt;

        public GetProductResponseDto(Product product, Boolean isBookmarked) {
            this.productId = product.getId();
            this.brand = product.getBrand();
            this.name = product.getName();
            this.price = product.getPrice();
            this.discount = product.getDiscount();
            this.imageUrl = product.getImageUrl();
            this.isFreeDelivery = product.isFreeDelivery();
            this.isSpecialPrice = product.isSpecialPrice();
            this.isBookmarked = isBookmarked;
            this.createdAt = product.getCreatedAt();
            this.modifiedAt = product.getModifiedAt();
        }
    }

    @Getter
    public static class GetProductBookmarkResponseDto {
        private final Long productId;
        private final String brand;
        private final String name;
        private final double discount;
        private final long price;
        private final String imageUrl;
        private final Boolean isFreeDelivery;
        private final Boolean isSpecialPrice;
        private final Boolean isBookmarked;
        private final long countReview;
        private final double averageRating;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime createdAt;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime modifiedAt;


        public GetProductBookmarkResponseDto(Product product, Boolean isBookmarked, long countReview, double averageRating) {
            this.productId = product.getId();
            this.brand = product.getBrand();
            this.name = product.getName();
            this.price = product.getPrice();
            this.discount = product.getDiscount();
            this.imageUrl = product.getImageUrl();
            this.isFreeDelivery = product.isFreeDelivery();
            this.isSpecialPrice = product.isSpecialPrice();
            this.createdAt = product.getCreatedAt();
            this.modifiedAt = product.getModifiedAt();
            this.isBookmarked = isBookmarked;
            this.countReview = countReview;
            this.averageRating = averageRating;
        }
    }

    @Getter
    public static class GetProductListResponseDto {
        private final List<GetProductBookmarkResponseDto> products;

        public GetProductListResponseDto(List<GetProductBookmarkResponseDto> products) {
            this.products = products;
        }
    }

    @Getter
    public static class GetProductReviewResponseDto {
        private final GetProductResponseDto product;
        private final List<GetReviewResponseDto> reviews;

        public GetProductReviewResponseDto(
                GetProductResponseDto product, List<GetReviewResponseDto> reviews
        ) {
            this.product = product;
            this.reviews = reviews;
        }
    }

    @Getter
    public static class SearchRankProductResponseDto {

        private final String keyword;
        private final double score;

        public SearchRankProductResponseDto(String keyword, double score) {
            this.keyword = keyword;
            this.score = score;
        }
    }
}
