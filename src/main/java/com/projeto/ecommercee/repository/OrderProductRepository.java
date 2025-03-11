package com.projeto.ecommercee.repository;


import com.projeto.ecommercee.entity.OrderProduct;
import com.projeto.ecommercee.entity.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {
}
