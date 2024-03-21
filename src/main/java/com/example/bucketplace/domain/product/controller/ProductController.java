package com.example.bucketplace.domain.product.controller;

import com.example.bucketplace.domain.product.controller.docs.ProductControllerDocs;
import com.example.bucketplace.domain.product.dto.ProductResponseDto.*;
import com.example.bucketplace.domain.product.service.ProductService;
import com.example.bucketplace.global.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/products")
@RestController
public class ProductController implements ProductControllerDocs {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseDto<GetProductListResponseDto> getProducts(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        GetProductListResponseDto products = productService.getProducts(page, size);
        return ResponseDto.success("전체 상품 조회 기능", products);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{productId}")
    public ResponseDto<GetProductReviewResponseDto> getProductDetail(@PathVariable Long productId) {
        GetProductReviewResponseDto product = productService.getProductDetail(productId);
        return ResponseDto.success("선택 상품 조회 기능", product);
    }
}
