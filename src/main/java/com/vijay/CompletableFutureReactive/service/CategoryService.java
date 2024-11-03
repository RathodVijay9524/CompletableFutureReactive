package com.vijay.CompletableFutureReactive.service;

import com.vijay.CompletableFutureReactive.entity.Category;
import com.vijay.CompletableFutureReactive.exception.ResourceNotFoundException;
import com.vijay.CompletableFutureReactive.mapper.Mapper;
import com.vijay.CompletableFutureReactive.repository.CategoryRepository;
import com.vijay.CompletableFutureReactive.request.CategoryRequest;
import com.vijay.CompletableFutureReactive.response.CategoryResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final Mapper mapper;

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> mapper.toDto(category, CategoryResponse.class))
                .toList();
    }

    public CategoryResponse getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(category -> mapper.toDto(category, CategoryResponse.class))
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = Mapper.toEntity(categoryRequest, Category.class,"id");
        Category savedCategory = categoryRepository.save(category);
        return Mapper.toDto(savedCategory, CategoryResponse.class);
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        Category updatedCategory = Mapper.toEntity(categoryRequest, Category.class, "id");
        updatedCategory.setId(category.getId());
        categoryRepository.save(updatedCategory);
        return Mapper.toDto(updatedCategory, CategoryResponse.class);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        categoryRepository.delete(category);
    }
}
