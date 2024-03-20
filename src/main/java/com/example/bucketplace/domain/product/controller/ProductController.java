package com.example.bucketplace.domain.product.controller;

import com.example.bucketplace.domain.product.controller.docs.ProductControllerDocs;
import com.example.bucketplace.domain.product.dto.ProductResponseDto.*;
import com.example.bucketplace.domain.product.service.ProductService;
import com.example.bucketplace.global.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/products")
@RestController
public class ProductController implements ProductControllerDocs {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseDto<GetProductListResponseDto> getProducts() {
        GetProductListResponseDto responseDtoList = productService.getProducts();
        return ResponseDto.success("전체 상품 조회 기능", responseDtoList);
    }


}
