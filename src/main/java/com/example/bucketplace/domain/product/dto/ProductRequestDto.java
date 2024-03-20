package com.example.bucketplace.domain.product.dto;

import lombok.Getter;

@Getter
public class ProductRequestDto {
    private String brand;
    private String name;
    private double discount;
    private long price;
    private String imageUrl;
    private boolean isFreeDelivery;
    private boolean isSpecialPrice;
}
