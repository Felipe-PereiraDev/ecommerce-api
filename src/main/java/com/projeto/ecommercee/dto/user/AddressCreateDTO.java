package com.projeto.ecommercee.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

public record AddressCreateDTO(
        @Schema(description = "CEP", example = "78065-318")
        String zipCode,
        @Schema(description = "Nome do Estado", example = "Mato Grosso")
        String state,
        @Schema(description = "Nome da Cidade", example = "Cuiabá")
        String city,
        @Schema(description = "Nome da rua", example = "feijão verde")
        String street
) {
}       
