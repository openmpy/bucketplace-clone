package com.example.bucketplace.domain.bookmark.controller.docs;

import com.example.bucketplace.domain.bookmark.dto.BookmarkResponseDto;
import com.example.bucketplace.global.dto.ResponseDto;
import com.example.bucketplace.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "bookmarks", description = "북마크 관련 API")
public interface BookmarkControllerDocs {

    @Operation(summary = "북마크 등록 기능", description = "북마크를 등록할 수 있는 API")
    ResponseDto<BookmarkResponseDto.CreateBookmarkResponseDto> createBookmark(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long productId
    );
}
