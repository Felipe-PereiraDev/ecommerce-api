package com.projeto.ecommerce.entity;

import com.projeto.ecommerce.dto.category.CategoryUpdateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;


@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, name = "name", length = 80)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant createdAt;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updatedAt;

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }

    public void setName(String name) {
        if (name.length() < 3 || name.length() > 80) {
            throw new IllegalArgumentException("O nome deve ter entre 3 e 80 caracteres.");
        }
        this.name = name;
    }

    public void setDescription(String description) {
        if (description.length() < 10 || description.length() > 255) {
            throw new IllegalArgumentException("A descrição tem que ter entre 10 a 255 caracteres caracteres.");
        }
        this.description = description;
    }

    public void update(CategoryUpdateDTO data) {
        if (data.name() != null) {
            setName(data.name());
        }
        if (data.description() != null) {
            setDescription(data.description());
        }
    }
}
