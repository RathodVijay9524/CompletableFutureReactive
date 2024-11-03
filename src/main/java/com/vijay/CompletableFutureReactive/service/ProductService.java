package com.vijay.CompletableFutureReactive.service;

import com.vijay.CompletableFutureReactive.entity.Category;
import com.vijay.CompletableFutureReactive.entity.Product;
import com.vijay.CompletableFutureReactive.exception.ResourceNotFoundException;
import com.vijay.CompletableFutureReactive.mapper.Mapper;
import com.vijay.CompletableFutureReactive.repository.CategoryRepository;
import com.vijay.CompletableFutureReactive.repository.ProductRepository;
import com.vijay.CompletableFutureReactive.request.ProductRequest;
import com.vijay.CompletableFutureReactive.response.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final Mapper mapper;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .price(product.getPrice())
                        .name(product.getName())
                        .description(product.getDescription())
                        .categoryId(product.getCategory().getId())
                        .build())
                .collect(Collectors.toList());
    }

    public ProductResponse getProductById(Long id) {
        return productRepository.findById(id)
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .price(product.getPrice())
                        .name(product.getName())
                        .description(product.getDescription())
                        .categoryId(product.getCategory().getId())
                        .build())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        log.info("Creating product: {}", productRequest); // Fetch category
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productRequest.getCategoryId()));
        // Map request to entity
        Product product = mapper.toEntity(productRequest, Product.class, "id");
        product.setCategory(category);
        // Save product
        Product savedProduct = productRepository.save(product);
        // Log saved product
        log.info("Saved product: {}", savedProduct);
        // Map entity to response
        return ProductResponse.builder()
                .id(savedProduct.getId())
                .price(savedProduct.getPrice())
                .name(product.getName())
                .description(savedProduct.getDescription())
                .categoryId(savedProduct.getCategory().getId())
                .build();
    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productRequest.getCategoryId()));
        Product updatedProduct = mapper.toEntity(productRequest, Product.class, "id");
        updatedProduct.setId(existingProduct.getId());
        updatedProduct.setCategory(category);
        Product savedProduct = productRepository.save(updatedProduct);
        return ProductResponse.builder()
                .id(savedProduct.getId())
                .price(savedProduct.getPrice())
                .name(savedProduct.getName())
                .description(savedProduct.getDescription())
                .categoryId(savedProduct.getCategory().getId())
                .build();
    }


    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }
}
