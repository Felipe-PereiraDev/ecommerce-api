package com.projeto.ecommerce.controller.docs;

import com.projeto.ecommerce.dto.order.CreateOrderDTO;
import com.projeto.ecommerce.dto.order.OrderResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Order", description = "Endpoints para gerenciar pedidos")
public interface OrderApi {
    @Operation(summary = "Fazer pedido",
            description = "Cria um novo pedido para o usuário especificado pelo ID. O pedido deve conter os itens e detalhes necessários.")
    @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso")
    @ApiResponse(responseCode = "403", description = "Acesso negado")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    ResponseEntity<OrderResponseDTO> createOrder(@PathVariable("userId") String userId,
                                                        @RequestBody @Validated CreateOrderDTO data);

    @Operation(summary = "Listar todos os pedidos", description = "Retorna a lista de todos os pedidos do banco de dados.")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    ResponseEntity<Page<OrderResponseDTO>> listAllOrders(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                @RequestParam(value = "size", defaultValue = "10") int size,
                                                                @RequestParam(value = "sort", defaultValue = "orderDate") String sort);

    @Operation(summary = "Listar todos os pedidos de um usuário", description = "Retorna a lista de todos os pedidos feitos por um usuário especificado pelo ID.")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "403", description = "Acesso negado")
    ResponseEntity<List<OrderResponseDTO>> listUserOrders(@PathVariable("userId") String userId);

    @Operation(summary = "Buscar pedido por ID e usuário", description = "Retorna um pedido específico a partir do ID do usuário e do pedido.")
    @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Pedido ou usuário não encontrado")
    @ApiResponse(responseCode = "403", description = "Acesso negado")
    ResponseEntity<OrderResponseDTO> findUserOrderById(@PathVariable("userId") String userId,
                                                              @PathVariable("orderId") Long orderId);
}
