package com.example.bucketplace.domain.product.controller;

import com.example.bucketplace.domain.product.controller.docs.ProductControllerDocs;
import com.example.bucketplace.domain.product.dto.ProductResponseDto.GetProductListResponseDto;
import com.example.bucketplace.domain.product.dto.ProductResponseDto.GetProductResponseDto;
import com.example.bucketplace.domain.product.dto.ProductResponseDto.GetProductReviewResponseDto;
import com.example.bucketplace.domain.product.dto.ProductResponseDto.SearchRankProductResponseDto;
import com.example.bucketplace.domain.product.service.ProductService;
import com.example.bucketplace.global.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @AuthenticationPrincipal @Nullable UserDetails userDetails,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        GetProductListResponseDto products = productService.getProducts(
                userDetails != null ? userDetails.getUsername() : null, page, size
        );
        return ResponseDto.success("전체 상품 조회 기능", products);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{productId}")
    public ResponseDto<GetProductReviewResponseDto> getProductDetail(
            @PathVariable Long productId,
            @AuthenticationPrincipal @Nullable UserDetails userDetails
    ) {
        GetProductReviewResponseDto product = productService.getProductDetail(
                userDetails != null ? userDetails.getUsername() : null, productId
        );
        return ResponseDto.success("선택 상품 조회 기능", product);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public ResponseDto<List<GetProductResponseDto>> findProduct(
            @RequestParam String name,
            @AuthenticationPrincipal @Nullable UserDetails userDetails
    ) {
        List<GetProductResponseDto> findProductResponseDto = productService.findProduct(
                userDetails != null ? userDetails.getUsername() : null, name
        );
        return ResponseDto.success("상품 검색 기능", findProductResponseDto);
    }

    @GetMapping("/search-rank")
    public ResponseDto<List<SearchRankProductResponseDto>> searchRankProduct() {
        List<SearchRankProductResponseDto> responseDtoList = productService.searchRankProduct();
        return ResponseDto.success("실시간 상품 검색 순위 기능", responseDtoList);
    }
}
