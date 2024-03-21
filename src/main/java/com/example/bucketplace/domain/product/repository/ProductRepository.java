package com.example.bucketplace.domain.product.repository;

import com.example.bucketplace.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);

    Optional<Product> findById(Long id);

    List<Product> findProductByNameContaining(String name);
}
