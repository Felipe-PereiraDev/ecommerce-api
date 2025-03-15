package com.projeto.ecommerce.service;

import com.projeto.ecommerce.dto.order.CreateOrderDTO;
import com.projeto.ecommerce.dto.order.OrderProductResponse;
import com.projeto.ecommerce.dto.order.OrderResponseDTO;
import com.projeto.ecommerce.dto.order.ProductOrderDTO;
import com.projeto.ecommerce.entity.*;
import com.projeto.ecommerce.exception.InsufficientStockException;
import com.projeto.ecommerce.exception.MissingAddressException;
import com.projeto.ecommerce.exception.OrderNotFoundException;
import com.projeto.ecommerce.repository.OrderProductRepository;
import com.projeto.ecommerce.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final UserService userService;
    private final OrderProductRepository orderProductRepository;

    public OrderService(OrderRepository orderRepository, ProductService productService, UserService userService, OrderProductRepository orderProductRepository) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.userService = userService;
        this.orderProductRepository = orderProductRepository;
    }

    public OrderResponseDTO findById(String userId, Long orderId) {
        userService.findById(userId);
        var order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        return new OrderResponseDTO(order,listOrderProductsResponse(order.getOrderProducts()));
    }


    public List<OrderResponseDTO> findAllUserOrders(String userId) {
        var user = userService.findById(userId);
        return user.getOrders().stream()
                .map(orderItem ->
                        new OrderResponseDTO(orderItem, listOrderProductsResponse(orderItem.getOrderProducts())))
                .toList();
    }

    @Transactional
    public OrderResponseDTO createOrder(String userId, CreateOrderDTO data) {
        User user = userService.findById(userId);
        if (user.getAddress() == null) {
            throw new MissingAddressException();
        }
        Order order = new Order();
        order.setUser(user);
        List<OrderProduct> listOrderProducts = insertListOrderProduct(data.products(), order);
        Double totalAmount = getTotalAmount(listOrderProducts);

        order.setOrderProducts(listOrderProducts);
        order.setTotalAmount(totalAmount);

        Order createdOrder = orderRepository.save(order);
        user.addOrders(createdOrder);

        orderProductRepository.saveAll(listOrderProducts);
        userService.save(user);
        return new OrderResponseDTO(createdOrder, listOrderProductsResponse(listOrderProducts));
    }

    private Double getTotalAmount(List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .mapToDouble(orderProduct -> orderProduct.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(orderProduct.getQuantity()))
                        .doubleValue())
                .sum();
    }

    private List<OrderProduct> insertListOrderProduct(List<ProductOrderDTO> orderProductDTOS, Order order) {
        Map<Long, OrderProduct> orderProductMap = new HashMap<>();
        for (ProductOrderDTO orderDTO : orderProductDTOS) {
            Product product = productService.findById(orderDTO.productId());

            checkStockAvailability(product, orderDTO.quantity());

            if (orderProductMap.containsKey(product.getId())) {
                orderProductMap.get(product.getId()).addQuantity(orderDTO.quantity());
            } else {
                OrderProduct orderProduct = new OrderProduct(product, order, orderDTO.quantity());
                orderProductMap.put(product.getId(), orderProduct);
            }
        }
        return new ArrayList<>(orderProductMap.values());
    }


    private List<OrderProductResponse> listOrderProductsResponse(List<OrderProduct> orderProducts) {
        return orderProducts.stream().map(OrderProductResponse::new).toList();
    }

    private void checkStockAvailability(Product product, Long quantity) {
        if (product.getStockQuantity() < quantity) {
            throw new InsufficientStockException(product.getName());
        }
    }

    private Long getQuantityForProduct(Long productId, List<ProductOrderDTO> orderDTO) {
        return orderDTO.stream().filter(order -> order.productId().equals(productId))
                .mapToLong(ProductOrderDTO::quantity)
                .findFirst()
                .orElse(0);
    }

    public Page<OrderResponseDTO> findAll(int page, int size, String sort) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());
            return orderRepository.findAll(pageable).map(order -> new OrderResponseDTO(order, listOrderProductsResponse(order.getOrderProducts())));
        } catch (PropertyReferenceException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


}
