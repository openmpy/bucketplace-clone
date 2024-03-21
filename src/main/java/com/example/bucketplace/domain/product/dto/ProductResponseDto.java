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

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime createdAt;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime modifiedAt;

        public GetProductResponseDto(Product product) {
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
        }
    }

    @Getter
    public static class GetProductListResponseDto {
        private final List<GetProductResponseDto> products;

        public GetProductListResponseDto(List<GetProductResponseDto> products) {
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


}
