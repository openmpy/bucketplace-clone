package com.example.bucketplace.domain.bookmark.controller.docs;

import com.example.bucketplace.domain.bookmark.dto.BookmarkResponseDto;
import com.example.bucketplace.domain.bookmark.dto.BookmarkResponseDto.CheckBookmarkResponseDto;
import com.example.bucketplace.domain.bookmark.dto.BookmarkResponseDto.GetBookmarkResponseDto;
import com.example.bucketplace.global.dto.ResponseDto;
import com.example.bucketplace.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "bookmarks", description = "북마크 관련 API")
public interface BookmarkControllerDocs {

    @Operation(summary = "북마크 등록 기능", description = "북마크를 등록할 수 있는 API")
    ResponseDto<BookmarkResponseDto.CreateBookmarkResponseDto> createBookmark(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long productId
    );

    @Operation(summary = "북마크 등록 여부 확인 기능", description = "북마크 등록 여부를 확인할 수 있는 API")
    ResponseDto<CheckBookmarkResponseDto> checkBookmark(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long productId
    );

    @Operation(summary = "북마크 삭제 기능", description = "북마크를 삭제할 수 있는 API")
    void deleteBookmark(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id
    );

    @Operation(summary = "회원 북마크 목록 기능", description = "북마크를 삭제할 수 있는 API")
    ResponseDto<Page<GetBookmarkResponseDto>> getBookmarkPage(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    );
}
