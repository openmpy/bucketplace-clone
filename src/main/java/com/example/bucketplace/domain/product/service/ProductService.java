package com.example.bucketplace.domain.product.service;

import com.example.bucketplace.domain.bookmark.repository.BookmarkRepository;
import com.example.bucketplace.domain.member.entity.Member;
import com.example.bucketplace.domain.member.repository.MemberRepository;
import com.example.bucketplace.domain.product.dto.ProductResponseDto.*;
import com.example.bucketplace.domain.product.entity.Product;
import com.example.bucketplace.domain.product.repository.ProductRepository;
import com.example.bucketplace.domain.review.dto.ReviewResponseDto.GetReviewResponseDto;
import com.example.bucketplace.domain.review.repository.ReviewRepository;
import com.example.bucketplace.global.exception.BadRequestException;
import com.example.bucketplace.global.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;

    public ProductService(ProductRepository productRepository, ReviewRepository reviewRepository,
                          RedisTemplate<String, String> redisTemplate, BookmarkRepository bookmarkRepository, MemberRepository memberRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.redisTemplate = redisTemplate;
        this.bookmarkRepository = bookmarkRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public GetProductListResponseDto getProducts(String email, int page, int size) {
        Member member = memberRepository.findByEmail(email).orElse(null);

        Page<Product> productList = productRepository.findAll(PageRequest.of(Math.max(0, page - 1), size));

        List<GetProductBookmarkResponseDto> dtos = productList.stream()
                .map(product -> {
                    boolean isBookmarked = bookmarkRepository.existsByMemberAndProduct(member, product);
                    long countReview = reviewRepository.countByProduct(product);
                    double averageRating = reviewRepository.findAverageRatingByProduct(product);
                    return new GetProductBookmarkResponseDto(product, isBookmarked, countReview, averageRating);
                })
                .collect(Collectors.toList());

        return new GetProductListResponseDto(dtos);
    }

    @Transactional(readOnly = true)
    public GetProductReviewResponseDto getProductDetail(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new BadRequestException(ErrorCode.NOT_FOUND_PRODUCT.getMessage()));
        List<GetReviewResponseDto> reviews = reviewRepository.findAllByProductId(productId).stream()
                .map(GetReviewResponseDto::new)
                .toList();
        return new GetProductReviewResponseDto(new GetProductResponseDto(product), reviews);
    }

    @Transactional(readOnly = true)
    public List<GetProductResponseDto> findProduct(String name) {
        List<Product> productList = productRepository.findProductByNameContaining(name);
        redisTemplate.opsForZSet().incrementScore("ranking", name, 1);

        return productList.stream()
                .map(GetProductResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SearchRankProductResponseDto> searchRankProduct() {
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> ranking = zSetOperations.reverseRangeWithScores("ranking", 0, 9);

        return ranking.stream()
                .map(tuple -> new SearchRankProductResponseDto(tuple.getValue(), tuple.getScore()))
                .toList();
    }
}
