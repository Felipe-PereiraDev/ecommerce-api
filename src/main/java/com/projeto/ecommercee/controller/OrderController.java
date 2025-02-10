package com.projeto.ecommercee.controller;

import com.projeto.ecommercee.dto.order.CreateOrderDTO;
import com.projeto.ecommercee.dto.order.OrderResponseDTO;
import com.projeto.ecommercee.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<OrderResponseDTO> createOrder(@PathVariable("userId") String userId,
                                                        @RequestBody @Validated CreateOrderDTO data) {
        OrderResponseDTO orderResponseDTO = orderService.createOrder(userId, data);
        return ResponseEntity.ok(orderResponseDTO);
    }


}
