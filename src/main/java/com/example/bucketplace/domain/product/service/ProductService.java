package com.example.bucketplace.domain.product.service;

import com.example.bucketplace.domain.product.dto.ProductResponseDto.GetProductListResponseDto;
import com.example.bucketplace.domain.product.dto.ProductResponseDto.GetProductResponseDto;
import com.example.bucketplace.domain.product.entity.Product;
import com.example.bucketplace.domain.product.repository.ProductRepository;
import com.example.bucketplace.global.exception.BadRequestException;
import com.example.bucketplace.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public GetProductListResponseDto getProducts() {
        List<GetProductResponseDto> products = productRepository.findAll().stream()
                .map(GetProductResponseDto::new)
                .toList();
        return new GetProductListResponseDto(products);
    }

    @Transactional(readOnly = true)
    public GetProductResponseDto getProductDetail(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()->
                new BadRequestException(ErrorCode.NOT_FOUND_PRODUCT.getMessage()));
        return new GetProductResponseDto(product);
    }
}
