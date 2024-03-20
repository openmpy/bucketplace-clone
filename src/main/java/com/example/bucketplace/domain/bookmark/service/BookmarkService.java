package com.example.bucketplace.domain.bookmark.service;

import com.example.bucketplace.domain.bookmark.dto.BookmarkResponseDto.CreateBookmarkResponseDto;
import com.example.bucketplace.domain.bookmark.entity.Bookmark;
import com.example.bucketplace.domain.bookmark.repository.BookmarkRepository;
import com.example.bucketplace.domain.member.entity.Member;
import com.example.bucketplace.domain.member.repository.MemberRepository;
import com.example.bucketplace.domain.product.entity.Product;
import com.example.bucketplace.domain.product.repository.ProductRepository;
import com.example.bucketplace.global.exception.BadRequestException;
import com.example.bucketplace.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository, MemberRepository memberRepository, ProductRepository productRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public CreateBookmarkResponseDto createBookmark(String email, Long productId) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new BadRequestException(ErrorCode.NOT_FOUND_MEMBER_EMAIL.getMessage())
        );
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new BadRequestException(ErrorCode.NOT_FOUND_PRODUCT_ID.getMessage())
        );
        if (bookmarkRepository.existsByMemberAndProduct(member, product)) {
            throw new BadRequestException(ErrorCode.ALREADY_EXIST_BOOKMARK.getMessage());
        }

        Bookmark bookmark = bookmarkRepository.save(Bookmark.builder()
                .member(member)
                .product(product)
                .build()
        );

        return new CreateBookmarkResponseDto(bookmark);
    }
}
