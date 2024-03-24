package com.example.bucketplace.domain.review.repository;

import com.example.bucketplace.domain.product.entity.Product;
import com.example.bucketplace.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByProductId(Long id);
    // 특정 상품에 대한 리뷰의 수를 카운트하는 메서드
    long countByProduct(Product product);

    // 특정 상품에 대한 리뷰의 평균 평점을 구하는 메서드
    @Query("SELECT COALESCE(AVG(r.rating), 0.0) FROM Review r WHERE r.product = :product")
    Double findAverageRatingByProduct(Product product);
}
