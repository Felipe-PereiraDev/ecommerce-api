package com.projeto.ecommerce.controller;

import com.projeto.ecommerce.controller.docs.OrderApi;
import com.projeto.ecommerce.dto.order.CreateOrderDTO;
import com.projeto.ecommerce.dto.order.OrderResponseDTO;
import com.projeto.ecommerce.service.AuthenticationService;
import com.projeto.ecommerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
public class OrderController implements OrderApi {
    @Autowired
    private OrderService orderService;
    @Autowired
    private AuthenticationService authenticationService;

    @PreAuthorize("@authenticationService.hasAccessPermission(#userId)")
    @PostMapping("/{userId}")
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
