package com.example.bucketplace.domain.bookmark.repository;

import com.example.bucketplace.domain.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}
