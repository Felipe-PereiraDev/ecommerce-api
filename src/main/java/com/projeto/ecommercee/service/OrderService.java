package com.projeto.ecommercee.service;

import com.projeto.ecommercee.dto.order.OrderProductResponse;

import com.projeto.ecommercee.dto.order.CreateOrderDTO;
import com.projeto.ecommercee.dto.order.OrderResponseDTO;
import com.projeto.ecommercee.dto.order.ProductOrderDTO;
import com.projeto.ecommercee.entity.*;
import com.projeto.ecommercee.exception.InsufficientStockException;
import com.projeto.ecommercee.exception.MissingAddressException;
import com.projeto.ecommercee.repository.OrderProductRepository;
import com.projeto.ecommercee.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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


    public OrderResponseDTO createOrder(String userId, CreateOrderDTO data){
        User user = userService.findById(userId);
        if (user.getAddress() == null) {
            throw new MissingAddressException();
        }
        List<Product> listProducts = data.products().stream().map(
                p -> productService.findById(p.productId())
        ).toList();

        Order order = new Order();
        order.setUser(user);
        System.out.println("ID ORDER:"+ order.getId());

        List<OrderProduct> listOrderProducts = new ArrayList<>();


        for (ProductOrderDTO orderDTO : data.products()) {
            Product product = productService.findById(orderDTO.productId());
            checkStockAvailability(product, orderDTO.quantity());

            OrderProductId orderProductId = new OrderProductId(order.getId(), product.getId());

            OrderProduct orderProduct = new OrderProduct(orderProductId, product, order, orderDTO.quantity());
            listOrderProducts.add(orderProduct);
        }

        double totalAmount = listOrderProducts.stream()
                .mapToDouble(orderProduct -> orderProduct.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(orderProduct.getQuantity()))
                        .doubleValue())
                .sum();

        order.setOrderProducts(listOrderProducts);
        order.setTotalAmount(totalAmount);

        System.out.println("ID ORDER antes do save: " + order.getId());
        order = orderRepository.save(order);
        System.out.println("ID ORDER depois do save: " + order.getId());


        Order createdOrder = orderRepository.save(order);


        user.addOrders(createdOrder);

        orderProductRepository.saveAll(listOrderProducts);

        userService.save(user);

        return new OrderResponseDTO(order, listOrderProducts(listOrderProducts));
    }

    private List<OrderProductResponse> listOrderProducts(List<OrderProduct> orderProducts) {
        List<OrderProductResponse> list = new ArrayList<>();
        for (OrderProduct oP : orderProducts) {
            list.add(new OrderProductResponse(oP.getProduct(), oP.getQuantity()));
        }
        return list;
    }

    private void checkStockAvailability(Product product, Long quantity) {
        if (product.getStockQuantity() < quantity) {
            throw  new InsufficientStockException(product.getName());
        }
    }

    private Long getQuantityForProduct(Long productId, List<ProductOrderDTO> orderDTO) {
        return orderDTO.stream().filter(order -> order.productId().equals(productId))
                .mapToLong(ProductOrderDTO::quantity)
                .findFirst()
                .orElse(0);
    }
}
