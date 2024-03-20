package com.example.bucketplace.domain.product.service;

import com.example.bucketplace.domain.product.dto.ProductResponseDto.*;
import com.example.bucketplace.domain.product.entity.Product;
import com.example.bucketplace.domain.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public GetProductListResponseDto getProducts() {
        List<Product> productList = productRepository.findAll();
        List<GetProductResponseDto> productResponseDtoList = productList.stream()
                .map(GetProductResponseDto::new)
                .collect(Collectors.toList());
        return new GetProductListResponseDto(productResponseDtoList);
    }
}
