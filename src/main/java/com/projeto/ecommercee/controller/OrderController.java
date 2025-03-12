package com.projeto.ecommercee.controller;

import com.projeto.ecommercee.dto.order.CreateOrderDTO;
import com.projeto.ecommercee.dto.order.OrderResponseDTO;
import com.projeto.ecommercee.service.AuthenticationService;
import com.projeto.ecommercee.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;
    private final AuthenticationService authenticationService;

    public OrderController(OrderService orderService, AuthenticationService authenticationService) {
        this.orderService = orderService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/{userId}")
    @PreAuthorize("@authenticationService.hasAccessPermission(#userId)")
    public ResponseEntity<OrderResponseDTO> createOrder(@PathVariable("userId") String userId,
                                                        @RequestBody @Validated CreateOrderDTO data) {
        OrderResponseDTO orderResponse = orderService.createOrder(userId, data);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(orderResponse.orderId()).toUri();
        return ResponseEntity.created(uri).body(orderResponse);
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponseDTO>> listAllOrders(@RequestParam(value = "page", defaultValue = "0") int page,
                                                             @RequestParam(value = "size", defaultValue = "10") int size,
                                                             @RequestParam(value = "sort", defaultValue = "orderDate") String sort) {
        return ResponseEntity.ok(orderService.findAll(page, size, sort));
    }


    @GetMapping("{userId}")
    @PreAuthorize("@authenticationService.hasAccessPermission(#userId)")
    public ResponseEntity<List<OrderResponseDTO>> listUserOrders(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(orderService.findAllUserOrders(userId));
    }

    @GetMapping("{userId}/{orderId}")
    @PreAuthorize("@authenticationService.hasAccessPermission(#userId)")
    public ResponseEntity<OrderResponseDTO> findUserOrderById(@PathVariable("userId") String userId,
                                                       @PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok(orderService.findById(userId, orderId));
    }



}
