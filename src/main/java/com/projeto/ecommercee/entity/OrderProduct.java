package com.projeto.ecommercee.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "order_products")
@Getter
@NoArgsConstructor
public class OrderProduct {

    @EmbeddedId
    private OrderProductId id;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal price;

    public OrderProduct(Product product, Order order, Long quantity) {
        this.id = new OrderProductId(order, product);
        this.quantity = quantity;
        this.price = product.getPrice();
    }

    public void addQuantity(Long quantity) {
        this.quantity += quantity;
    }

    public Product getProduct() {
        return id.getProduct();
    }

    public Order getOrder() {
        return id.getOrder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProduct that = (OrderProduct) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
