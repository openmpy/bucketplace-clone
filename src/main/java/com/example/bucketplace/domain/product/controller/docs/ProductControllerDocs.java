package com.example.bucketplace.domain.product.controller.docs;

import com.example.bucketplace.domain.product.dto.ProductResponseDto.*;
import com.example.bucketplace.global.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "products", description = "상품 관련 API")
public interface ProductControllerDocs {
    @Operation(summary = "전체 상품 조회 기능", description = "전체 상품 조회 할 수 있는 API")
    ResponseDto<GetProductListResponseDto> getProducts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    );

    @Operation(summary = "선택 상품 조회 기능", description = "선택 상품 조회 할 수 있는 API")
    ResponseDto<GetProductReviewResponseDto> getProductDetail(@PathVariable Long productId);
}
