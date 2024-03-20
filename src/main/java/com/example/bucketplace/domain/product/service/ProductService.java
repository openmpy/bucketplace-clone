package com.example.bucketplace.domain.product.service;

import com.example.bucketplace.domain.product.dto.ProductResponseDto.*;
import com.example.bucketplace.domain.product.entity.Product;
import com.example.bucketplace.domain.product.repository.ProductRepository;
import com.example.bucketplace.domain.review.dto.ReviewResponseDto.GetReviewResponseDto;
import com.example.bucketplace.domain.review.repository.ReviewRepository;
import com.example.bucketplace.global.exception.BadRequestException;
import com.example.bucketplace.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    public ProductService(ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    @Transactional(readOnly = true)
    public GetProductListResponseDto getProducts() {
        List<GetProductResponseDto> products = productRepository.findAll().stream()
                .map(GetProductResponseDto::new)
                .toList();
        return new GetProductListResponseDto(products);
    }

    @Transactional(readOnly = true)
    public GetProductReviewResponseDto getProductDetail(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()->
                new BadRequestException(ErrorCode.NOT_FOUND_PRODUCT.getMessage()));
        List <GetReviewResponseDto> reviews = reviewRepository.findAllByProductId(productId).stream()
                .map(GetReviewResponseDto::new)
                .toList();
        return new GetProductReviewResponseDto(new GetProductResponseDto(product), reviews);
    }
}
