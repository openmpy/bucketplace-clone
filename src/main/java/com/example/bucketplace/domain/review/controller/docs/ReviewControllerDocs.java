package com.example.bucketplace.domain.review.controller.docs;

import com.example.bucketplace.domain.review.dto.ReviewRequestDto;
import com.example.bucketplace.domain.review.dto.ReviewResponseDto;
import com.example.bucketplace.global.dto.ResponseDto;
import com.example.bucketplace.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "review", description = "리뷰 관련 API")
public interface ReviewControllerDocs {

    @Operation(summary = "리뷰 등록 기능", description = "리뷰 등록을 할 수 있는 API")
    ResponseDto<ReviewResponseDto.CreateReviewResponseDto> createReview(@PathVariable Long productId,
                                                                        @RequestBody @Valid ReviewRequestDto.CreateReviewRequestDto reviewRequestDto,
                                                                        @AuthenticationPrincipal UserDetailsImpl userDetails
    );

}
