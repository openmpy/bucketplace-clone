package com.example.bucketplace.domain.bookmark.dto;

import com.example.bucketplace.domain.bookmark.entity.Bookmark;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

public class BookmarkResponseDto {

    @Getter
    public static class CreateBookmarkResponseDto {

        private final Long productId;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime createdAt;

        public CreateBookmarkResponseDto(Bookmark bookmark) {
            this.productId = bookmark.getProduct().getId();
            this.createdAt = bookmark.getCreatedAt();
        }
    }
}
