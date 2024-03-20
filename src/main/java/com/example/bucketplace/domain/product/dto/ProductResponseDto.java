package com.example.bucketplace.domain.product.dto;

import com.example.bucketplace.domain.product.entity.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class ProductResponseDto {
    @Getter
    public static class GetProductResponseDto {
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
        private final List<GetProductResponseDto> responseDtoList;

        public GetProductListResponseDto(List<GetProductResponseDto> responseDtoList) {
            this.responseDtoList = responseDtoList;
        }
    }
}
