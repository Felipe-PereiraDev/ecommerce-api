package com.projeto.ecommercee.entity;

import com.projeto.ecommercee.dto.product.ProductCreateDto;
import com.projeto.ecommercee.dto.product.ProductUpdateDTO;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
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

    public Product(Long id, String name, String description, BigDecimal price, int stockQuantity, Category category, List<OrderProduct> orderProducts) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.orderProducts = orderProducts;
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }
}
