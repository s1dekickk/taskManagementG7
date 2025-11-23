package com.taskmanagement.service;
import com.taskmanagement.dtos.Category.CategoryDTO;
import com.taskmanagement.dtos.Category.CreateCategoryRequest;
import com.taskmanagement.dtos.Category.UpdateCategoryRequest;
import com.taskmanagement.entity.task.Category;
import com.taskmanagement.mapper.CategoryMapper;
import com.taskmanagement.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryDTO createCategory(CreateCategoryRequest request) {
        Category category = categoryMapper.toEntity(request);
        try {
            Category savedCategory = categoryRepository.save(category);
            return categoryMapper.toDto(savedCategory);
        } catch (DataIntegrityViolationException e) {
            // Catches unique constraint violation on the 'name' column
            throw new IllegalArgumentException("Category name '" + request.getName() + "' already exists.", e);
        }
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toDtoList(categories);
    }

    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + categoryId));
        return categoryMapper.toDto(category);
    }

    @Transactional
    public CategoryDTO updateCategory(Integer categoryId, UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + categoryId));

        categoryMapper.update(request, category);

        try {
            Category updatedCategory = categoryRepository.save(category);
            return categoryMapper.toDto(updatedCategory);
        } catch (DataIntegrityViolationException e) {
            // Catches unique constraint violation if the new name already exists
            throw new IllegalArgumentException("Category name '" + request.getName() + "' already exists.", e);
        }
    }

    @Transactional
    public void deleteCategory(Integer categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException("Category not found with ID: " + categoryId);
        }

        // Deletion will fail if any Task still references this Category,
        // leading to a DataIntegrityViolationException (foreign key constraint).
        try {
            categoryRepository.deleteById(categoryId);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete category as it is referenced by one or more tasks.", e);
        }
    }
}
