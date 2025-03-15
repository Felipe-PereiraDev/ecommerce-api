package com.projeto.ecommerce.dto.user;


import com.projeto.ecommerce.entity.Address;

public record AddressResponseDTO(
        String zipCode,
        String state,
        String city,
        String neighborhood,
        String street
) {
    public AddressResponseDTO(Address address){
        this(
                address.getZipCode(),
                address.getState(),
                address.getCity(),
                address.getNeighborhood(),
                address.getStreet()
        );
    }
}
