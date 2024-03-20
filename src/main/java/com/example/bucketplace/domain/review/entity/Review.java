package com.example.bucketplace.domain.review.entity;

import com.example.bucketplace.domain.member.entity.Member;
import com.example.bucketplace.domain.product.entity.Product;
import com.example.bucketplace.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "review_tbl")
@SQLDelete(sql = "UPDATE review_tbl SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Review extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean deleted = Boolean.FALSE;

    @Column(nullable = false)
    private String contents;

    @Column
    private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Builder
    public Review(boolean deleted, String contents, int rating, Member member, Product product) {
        this.deleted = deleted;
        this.contents = contents;
        this.rating = rating;
        this.member = member;
        this.product = product;
    }

    public void updateReview(String contents, int rating) {
        this.contents = contents;
        this.rating = rating;
    }
}
