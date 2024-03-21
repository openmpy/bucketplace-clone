package com.example.bucketplace.domain.bookmark.controller;

import com.example.bucketplace.domain.bookmark.controller.docs.BookmarkControllerDocs;
import com.example.bucketplace.domain.bookmark.dto.BookmarkResponseDto.CheckBookmarkResponseDto;
import com.example.bucketplace.domain.bookmark.dto.BookmarkResponseDto.CreateBookmarkResponseDto;
import com.example.bucketplace.domain.bookmark.dto.BookmarkResponseDto.GetBookmarkResponseDto;
import com.example.bucketplace.domain.bookmark.service.BookmarkService;
import com.example.bucketplace.global.dto.ResponseDto;
import com.example.bucketplace.global.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1")
@RestController
public class BookmarkController implements BookmarkControllerDocs {

    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/bookmarks/{productId}")
    public ResponseDto<CreateBookmarkResponseDto> createBookmark(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long productId
    ) {
        CreateBookmarkResponseDto responseDto = bookmarkService.createBookmark(userDetails.getUsername(), productId);
        return ResponseDto.success("북마크 등록 기능", responseDto);
    }

    @GetMapping("/bookmarks/{productId}")
    public ResponseDto<CheckBookmarkResponseDto> checkBookmark(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long productId
    ) {
        CheckBookmarkResponseDto responseDto = bookmarkService.checkBookmark(userDetails.getUsername(), productId);
        return ResponseDto.success("북마크 등록 여부 확인 기능", responseDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/bookmarks/{id}")
    public void deleteBookmark(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id
    ) {
        bookmarkService.deleteBookmark(userDetails.getUsername(), id);
    }

    @GetMapping("/members/bookmarks")
    public ResponseDto<List<GetBookmarkResponseDto>> getBookmarkPage(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        List<GetBookmarkResponseDto> responseDtoList = bookmarkService.getBookmarkList(userDetails.getUsername(), page, size);
        return ResponseDto.success("회원 북마크 목록 기능", responseDtoList);
    }
}
