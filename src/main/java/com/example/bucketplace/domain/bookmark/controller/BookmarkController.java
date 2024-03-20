package com.example.bucketplace.domain.bookmark.controller;

import com.example.bucketplace.domain.bookmark.controller.docs.BookmarkControllerDocs;
import com.example.bucketplace.domain.bookmark.dto.BookmarkResponseDto.CreateBookmarkResponseDto;
import com.example.bucketplace.domain.bookmark.service.BookmarkService;
import com.example.bucketplace.global.dto.ResponseDto;
import com.example.bucketplace.global.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/bookmarks")
@RestController
public class BookmarkController implements BookmarkControllerDocs {

    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{productId}")
    public ResponseDto<CreateBookmarkResponseDto> createBookmark(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long productId
    ) {
        CreateBookmarkResponseDto responseDto = bookmarkService.createBookmark(userDetails.getUsername(), productId);
        return ResponseDto.success("북마크 등록 기능", responseDto);
    }
}
