package com.projeto.ecommerce.repository;


import com.projeto.ecommerce.entity.OrderProduct;
import com.projeto.ecommerce.entity.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {
}
