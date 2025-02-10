package com.projeto.ecommercee.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "order_products")
public class OrderProduct {
    @EmbeddedId
    private OrderProductId id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private Long quantity;

    public OrderProduct(OrderProductId id, Product product, Order order, Long quantity) {
        this.id = id;
        this.product = product;
        this.order = order;
        this.quantity = quantity;
    }

    public OrderProduct(Product product, Order order, Long quantity) {
        this.product = product;
        this.order = order;
        this.quantity = quantity;
    }
    public OrderProduct() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
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
