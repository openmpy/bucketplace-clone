package com.example.bucketplace.domain.product.entity;

import com.example.bucketplace.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "product_tbl")
public class Product extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String name;

    @Column
    private long price;

    @Column
    private double discount;

    @Column(nullable = false)
    private String imageUrl;

    @Builder
    public Product(String brand, String name, long price, double discount, String imageUrl) {
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.imageUrl = imageUrl;
    }
}
