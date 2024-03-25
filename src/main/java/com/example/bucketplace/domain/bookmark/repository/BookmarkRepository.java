package com.example.bucketplace.domain.bookmark.repository;

import com.example.bucketplace.domain.bookmark.entity.Bookmark;
import com.example.bucketplace.domain.member.entity.Member;
import com.example.bucketplace.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    boolean existsByMemberAndProduct(Member member, Product product);

    Page<Bookmark> findAllByMemberOrderByCreatedAtDesc(Member member, Pageable pageable);

    Optional<Bookmark> findByMemberAndProduct(Member member, Product product);
}
