package com.projeto.ecommercee.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp
    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;


    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Order(UUID id, LocalDateTime orderDate, Double totalAmount, Status status, List<OrderProduct> orderProducts, User user) {
        this.id = id;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = Status.PENDING;
        this.orderProducts = orderProducts;
        this.user = user;
    }

    public Order() {
        this.status = Status.PENDING;
        this.totalAmount = 0.0;
    }


    public Order(double totalAmount, User user) {
        this.totalAmount = totalAmount;
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
