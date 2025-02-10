package com.projeto.ecommercee.dto.user;


import com.projeto.ecommercee.entity.Address;

public record AddressResponseDTO(
        String state,
        String city,
        String street
) {
    public AddressResponseDTO(Address address){
        this(
                address.getState(),
                address.getCity(),
                address.getStreet()
        );
    }
}
