package com.projeto.ecommercee.entity;

import com.projeto.ecommercee.dto.product.ProductCreateDto;
import com.projeto.ecommercee.dto.product.ProductUpdateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 80)
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

    @OneToMany(mappedBy = "id.product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();


    public Product(ProductCreateDto data, Category category) {
        this.name = data.name();
        this.description = data.description();
        this.changePrice(data.price());
        this.changeStockQuantity(data.stockQuantity());
        this.category = category;
    }

    public void changePrice(BigDecimal value) {
        if (value.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }
        price = value;
    }

    public void changeStockQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("A quantidade de estoque não pode ser negativa.");
        }
        stockQuantity = quantity;
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

    public List<Order> getOrders() {
        return orderProducts.stream().map(OrderProduct::getOrder).toList();
    }
}
