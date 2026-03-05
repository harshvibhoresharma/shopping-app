package com.example.shopping.service;

import com.example.shopping.model.Category;
import com.example.shopping.repository.CategoryRepository;
import com.example.shopping.service.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryService categoryService;
    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void shouldReturnAllCategories(){
        Category c1 = new Category();
        c1.setName("Electronics");
        Category c2 = new Category();
        c1.setName("Clothes");
        when(categoryRepository.findAll()).thenReturn(List.of(c1,c2));
        List<Category> categories = categoryService.getAllCategories();
        assertThat(categories.size()).isEqualTo(2);
        verify(categoryRepository,times(1)).findAll();
    }
    @Test
    void shouldReturnCategoryById(){
        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        Category result = categoryService.getCategoryById(1L);
        assertThat(result.getName()).isEqualTo("Electronics");
    }
    @Test
    void shouldCreateCategory(){
        Category category = new Category();
        category.setName("Electronics");
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        Category saved = categoryService.addCategory(category);
        assertThat(saved.getName()).isEqualTo("Electronics");
        verify(categoryRepository).save(any(Category.class));

    }

}
