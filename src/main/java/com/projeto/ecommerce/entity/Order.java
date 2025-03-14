package com.projeto.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;


    @Column(name = "total_amount", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @OneToMany(mappedBy = "id.order", fetch = FetchType.LAZY)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Order() {
        this.status = Status.PENDING;
        this.totalAmount = 0.0;
    }

    public Order(double totalAmount, User user) {
        this.totalAmount = totalAmount;
        this.user = user;
    }

    public List<Product> getProducts() {
        return orderProducts.stream().map(OrderProduct::getProduct).toList();
    }

}
