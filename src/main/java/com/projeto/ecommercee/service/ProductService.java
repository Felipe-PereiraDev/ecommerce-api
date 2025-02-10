package com.projeto.ecommercee.service;

import com.projeto.ecommercee.dto.product.ProductCreateDto;
import com.projeto.ecommercee.dto.product.ProductUpdateDTO;
import com.projeto.ecommercee.entity.Category;
import com.projeto.ecommercee.entity.Product;
import com.projeto.ecommercee.exception.ProductAlreadyExistsException;
import com.projeto.ecommercee.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;

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
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }


    public Product createProduct(ProductCreateDto data) {
        Category category = categoryService.findById(data.categoryId());
        Product newProduct = new Product(data, category);
        if (productRepository.existsByName(data.name())) {
            throw new ProductAlreadyExistsException();
        }
        return productRepository.save(newProduct);
    }

    public List<Product> getProducts() {
        return productRepository.findAll().stream().sorted(Comparator.comparing(Product::getName)).toList();
    }

    public void deleteById(Long id) {
        Product product = findById(id);
        productRepository.delete(product);
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
