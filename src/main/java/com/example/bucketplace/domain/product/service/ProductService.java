package com.example.bucketplace.domain.product.service;

import com.example.bucketplace.domain.product.dto.ProductResponseDto.*;
import com.example.bucketplace.domain.product.entity.Product;
import com.example.bucketplace.domain.product.repository.ProductRepository;
import com.example.bucketplace.domain.review.dto.ReviewResponseDto.GetReviewResponseDto;
import com.example.bucketplace.domain.review.repository.ReviewRepository;
import com.example.bucketplace.global.exception.BadRequestException;
import com.example.bucketplace.global.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public GetProductListResponseDto getProducts(int page, int size) {
        return new GetProductListResponseDto(
                productRepository.findAll(PageRequest.of(Math.max(0, page-1), size)).stream()
                        .map(GetProductResponseDto::new)
                        .toList()
        );
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
