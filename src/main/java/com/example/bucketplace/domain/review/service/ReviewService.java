package com.example.bucketplace.domain.review.service;

import com.example.bucketplace.domain.member.entity.Member;
import com.example.bucketplace.domain.member.repository.MemberRepository;
import com.example.bucketplace.domain.product.entity.Product;
import com.example.bucketplace.domain.product.repository.ProductRepository;
import com.example.bucketplace.domain.review.dto.ReviewRequestDto.CreateReviewRequestDto;
import com.example.bucketplace.domain.review.dto.ReviewRequestDto.UpdateReviewRequestDto;
import com.example.bucketplace.domain.review.dto.ReviewResponseDto.CreateReviewResponseDto;
import com.example.bucketplace.domain.review.dto.ReviewResponseDto.UpdateReviewResponseDto;
import com.example.bucketplace.domain.review.entity.Review;
import com.example.bucketplace.domain.review.repository.ReviewRepository;
import com.example.bucketplace.global.exception.BadRequestException;
import com.example.bucketplace.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public ReviewService(ReviewRepository reviewRepository, MemberRepository memberRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public CreateReviewResponseDto createReview(Long productId, String email, CreateReviewRequestDto reviewRequestDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException(ErrorCode.NOT_FOUND_MEMBER_EMAIL.getMessage()));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.NOT_FOUND_PRODUCT.getMessage()));

        Review review = reviewRepository.save(reviewRequestDto.toEntity(member, product));
        return new CreateReviewResponseDto(review);
    }

    @Transactional
    public UpdateReviewResponseDto updateReview(Long id, String email, UpdateReviewRequestDto reviewRequestDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException(ErrorCode.NOT_FOUND_MEMBER_EMAIL.getMessage()));

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorCode.NOT_FOUND_REVIEW.getMessage()));

        review.updateReview(reviewRequestDto.getContents(), reviewRequestDto.getRating());

        return new UpdateReviewResponseDto(review);
    }

    @Transactional
    public void deleteReview(Long id, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException(ErrorCode.NOT_FOUND_MEMBER_EMAIL.getMessage()));

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorCode.NOT_FOUND_REVIEW.getMessage()));

        if (!review.getMember().equals(member)) {
            throw new BadRequestException(ErrorCode.NOT_MATCH_REVIEW.getMessage());
        }

        reviewRepository.delete(review);
    }
}
