package com.example.bucketplace.domain.bookmark.service;

import com.example.bucketplace.domain.bookmark.dto.BookmarkResponseDto.CheckBookmarkResponseDto;
import com.example.bucketplace.domain.bookmark.dto.BookmarkResponseDto.CreateBookmarkResponseDto;
import com.example.bucketplace.domain.bookmark.dto.BookmarkResponseDto.GetBookmarkResponseDto;
import com.example.bucketplace.domain.bookmark.entity.Bookmark;
import com.example.bucketplace.domain.bookmark.repository.BookmarkRepository;
import com.example.bucketplace.domain.member.entity.Member;
import com.example.bucketplace.domain.member.repository.MemberRepository;
import com.example.bucketplace.domain.product.entity.Product;
import com.example.bucketplace.domain.product.repository.ProductRepository;
import com.example.bucketplace.global.exception.BadRequestException;
import com.example.bucketplace.global.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public CheckBookmarkResponseDto checkBookmark(String email, Long productId) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new BadRequestException(ErrorCode.NOT_FOUND_MEMBER_EMAIL.getMessage())
        );
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new BadRequestException(ErrorCode.NOT_FOUND_PRODUCT_ID.getMessage())
        );

        if (bookmarkRepository.existsByMemberAndProduct(member, product)) {
            return new CheckBookmarkResponseDto(true);
        }
        return new CheckBookmarkResponseDto(false);
    }

    @Transactional
    public void deleteBookmark(String email, Long id) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new BadRequestException(ErrorCode.NOT_FOUND_MEMBER_EMAIL.getMessage())
        );
        Bookmark bookmark = bookmarkRepository.findById(id).orElseThrow(() ->
                new BadRequestException(ErrorCode.NOT_FOUND_BOOKMARK_ID.getMessage())
        );
        if (!bookmark.getMember().equals(member)) {
            throw new BadRequestException(ErrorCode.NOT_MATCH_BOOKMARK_MEMBER.getMessage());
        }

        bookmarkRepository.delete(bookmark);
    }

    public Page<GetBookmarkResponseDto> getBookmarkPage(String email, Pageable pageable) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new BadRequestException(ErrorCode.NOT_FOUND_MEMBER_EMAIL.getMessage())
        );

        Page<Bookmark> bookmarkPage = bookmarkRepository.findAllByMemberOrderByCreatedAtDesc(member, pageable);
        return bookmarkPage.map(GetBookmarkResponseDto::new);
    }
}
