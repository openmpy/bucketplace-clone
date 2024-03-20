package com.example.bucketplace.domain.review.controller;

import com.example.bucketplace.domain.review.controller.docs.ReviewControllerDocs;
import com.example.bucketplace.domain.review.dto.ReviewRequestDto.CreateReviewRequestDto;
import com.example.bucketplace.domain.review.dto.ReviewRequestDto.UpdateReviewRequestDto;
import com.example.bucketplace.domain.review.dto.ReviewResponseDto.CreateReviewResponseDto;
import com.example.bucketplace.domain.review.dto.ReviewResponseDto.UpdateReviewResponseDto;
import com.example.bucketplace.domain.review.service.ReviewService;
import com.example.bucketplace.global.dto.ResponseDto;
import com.example.bucketplace.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1")
@RestController
public class ReviewController implements ReviewControllerDocs {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/products/{productId}/reviews")
    public ResponseDto<CreateReviewResponseDto> createReview(
            @PathVariable Long productId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid CreateReviewRequestDto reviewRequestDto) {
        CreateReviewResponseDto createReviewResponseDto = reviewService.createReview(productId, userDetails.getUsername(), reviewRequestDto);
        return ResponseDto.success("리뷰 등록 기능", createReviewResponseDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/products/{productId}/reviews/{id}")
    public ResponseDto<UpdateReviewResponseDto> updateReview(
            @PathVariable Long productId,
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid UpdateReviewRequestDto reviewRequestDto) {
        UpdateReviewResponseDto updateReviewResponseDto = reviewService.updateReview(productId, id, userDetails.getUsername(), reviewRequestDto);
        return ResponseDto.success("리뷰 수정 기능", updateReviewResponseDto);
    }

}
