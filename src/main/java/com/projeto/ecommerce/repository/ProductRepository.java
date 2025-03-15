package com.projeto.ecommerce.repository;


import com.projeto.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);

    Page<Product> findByCategory_NameIgnoreCase(String categoryName, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCase (String name, Pageable pageable);
}
