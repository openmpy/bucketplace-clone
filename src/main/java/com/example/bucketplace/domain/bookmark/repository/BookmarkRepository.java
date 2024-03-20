package com.example.bucketplace.domain.bookmark.repository;

import com.example.bucketplace.domain.bookmark.entity.Bookmark;
import com.example.bucketplace.domain.member.entity.Member;
import com.example.bucketplace.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    boolean existsByMemberAndProduct(Member member, Product product);
}
