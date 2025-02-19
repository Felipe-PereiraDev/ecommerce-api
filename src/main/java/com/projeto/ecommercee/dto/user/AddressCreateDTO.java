package com.projeto.ecommercee.dto.user;

public record AddressCreateDTO(
        String zipCode,
        String state,
        String city,
        String street
) {
}
