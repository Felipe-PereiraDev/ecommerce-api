package com.projeto.ecommerce.dto;

public record ViaCepDTO(
        String cep,
        String bairro,
        String estado,
        String localidade
) {
}
