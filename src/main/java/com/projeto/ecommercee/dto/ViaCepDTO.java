package com.projeto.ecommercee.dto;

public record ViaCepDTO(
        String cep,
        String bairro,
        String estado,
        String localidade
) {
}
