package com.example.bucketplace.domain.bookmark.dto;

import com.example.bucketplace.domain.bookmark.entity.Bookmark;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

public class BookmarkResponseDto {

    @Getter
    public static class CreateBookmarkResponseDto {

        private final Long id;
        private final Long productId;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private final LocalDateTime createdAt;

        public CreateBookmarkResponseDto(Bookmark bookmark) {
            this.id = bookmark.getId();
            this.productId = bookmark.getProduct().getId();
            this.createdAt = bookmark.getCreatedAt();
        }
    }

    @Getter
    public static class CheckBookmarkResponseDto {

        private final Boolean isExist;

        public CheckBookmarkResponseDto(Boolean isExist) {
            this.isExist = isExist;
        }
    }

    @Getter
    public static class GetBookmarkResponseDto {

        private final Long id;
        private final Long productId;
        private final String imageUrl;

        public GetBookmarkResponseDto(Bookmark bookmark) {
            this.id = bookmark.getId();
            this.productId = bookmark.getProduct().getId();
            this.imageUrl = bookmark.getProduct().getImageUrl();
        }
    }
}
