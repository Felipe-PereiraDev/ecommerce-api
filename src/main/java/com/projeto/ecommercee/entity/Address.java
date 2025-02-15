package com.projeto.ecommercee.entity;

import com.projeto.ecommercee.dto.user.AddressCreateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String state;
    private String city;
    private String street;

    public Address(AddressCreateDTO addressDTO) {
        this.state = addressDTO.state();
        this.city = addressDTO.city();
        this.street = addressDTO.street();
    }
}
