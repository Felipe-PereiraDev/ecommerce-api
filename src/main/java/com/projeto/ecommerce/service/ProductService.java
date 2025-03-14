package com.projeto.ecommerce.service;

import  com.projeto.ecommerce.dto.product.ProductCreateDto;
import com.projeto.ecommerce.dto.product.ProductResponseDTO;
import com.projeto.ecommerce.dto.product.ProductUpdateDTO;
import com.projeto.ecommerce.entity.Category;
import com.projeto.ecommerce.entity.Product;
import com.projeto.ecommerce.exception.DatabaseException;
import com.projeto.ecommerce.exception.ProductAlreadyExistsException;
import com.projeto.ecommerce.exception.ProductNotFoundException;
import com.projeto.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException(id)
        );
    }

    public Product createProduct(ProductCreateDto data) {
        if (productRepository.existsByName(data.name())) {
            throw new ProductAlreadyExistsException();
        }
        Category category = categoryService.findById(data.categoryId());
        Product newProduct = new Product(data, category);
        return productRepository.save(newProduct);
    }

    public Page<ProductResponseDTO> listAllProducts(Pageable pageable, String category) {
        try {
            if (StringUtils.hasText(category)) {
                 return productRepository.findByCategory_NameIgnoreCase(category, pageable).map(ProductResponseDTO::new);
            }
            return productRepository.findAll(pageable).map(ProductResponseDTO::new);
        } catch (PropertyReferenceException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public Page<ProductResponseDTO> searchProductsByName(Pageable pageable, String name) {
        try {
            return productRepository.findByNameContainingIgnoreCase(name.trim(), pageable).map(ProductResponseDTO::new);
        } catch (PropertyReferenceException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        try {
            productRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    @Transactional
    public Product updateProduct(Long id, ProductUpdateDTO updateProduct) {
        Product product = findById(id);
        if (updateProduct.categoryId() != null) {
            Category category = categoryService.findById(updateProduct.categoryId());
            product.updateProduct(updateProduct, category);
        } else {
            product.updateProduct(updateProduct, null);
        }
        productRepository.save(product);
        return product;
    }


}
