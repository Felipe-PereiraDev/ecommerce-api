package com.projeto.ecommercee.service;

import com.projeto.ecommercee.dto.product.ProductCreateDto;
import com.projeto.ecommercee.dto.product.ProductResponseDTO;
import com.projeto.ecommercee.dto.product.ProductUpdateDTO;
import com.projeto.ecommercee.entity.Category;
import com.projeto.ecommercee.entity.Product;
import com.projeto.ecommercee.exception.ProductAlreadyExistsException;
import com.projeto.ecommercee.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<ProductResponseDTO> getProducts(int page, int size, String sort, String category) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
            if (StringUtils.hasText(category)) {
                 return productRepository.findByCategory_NameIgnoreCase(category, pageable).map(ProductResponseDTO::new);
            }
            return productRepository.findAll(pageable).map(ProductResponseDTO::new);
        } catch (PropertyReferenceException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
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
