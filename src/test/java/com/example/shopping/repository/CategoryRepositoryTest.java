package com.example.shopping.repository;

import com.example.shopping.model.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;
    @Test
    @DisplayName("should save category")
    void shouldSaveCategory(){
        Category category = new Category();
        category.setName("Electronics");

        Category saved = categoryRepository.save(category);
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isGreaterThan(0);
        assertThat(saved.getName()).isEqualTo("Electronics");
    }
    @Test
    @DisplayName("should find category by name")
    void shouldFindCategoryByName() {

        Category category = new Category();
        category.setName("Electronics");

        categoryRepository.save(category);

        Category found = categoryRepository.findByName("Electronics");

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Electronics");
    }

}
