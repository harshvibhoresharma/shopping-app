package com.example.shopping.service.category;

import com.example.shopping.model.Category;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface iCategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category,Long id);
    void deleteCategoryById(Long id);

    boolean existsByName(@NotNull String name);
}
