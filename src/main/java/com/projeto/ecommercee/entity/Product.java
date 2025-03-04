package com.projeto.ecommercee.entity;

import com.projeto.ecommercee.dto.product.ProductCreateDto;
import com.projeto.ecommercee.dto.product.ProductUpdateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal price;

    @Column(nullable = false)
    private int stockQuantity;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant createdAt;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();


    public Product(ProductCreateDto data, Category category) {
        this.name = data.name();
        this.description = data.description();
        this.price = data.price();
        this.stockQuantity = data.stockQuantity();
        this.category = category;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }

    public void updateProduct(ProductUpdateDTO updateProduct, Category category){
        if (updateProduct.name() != null) {
            this.name = updateProduct.name();
        }
        if (updateProduct.description() != null) {
            this.description = updateProduct.description();
        }
        if (updateProduct.price() != null) {
            this.price = updateProduct.price();
        }

        if (updateProduct.stockQuantity() != null) {
            this.stockQuantity = updateProduct.stockQuantity();
        }
        if (category != null) {
            this.category = category;
        }
    }
}
