package com.example.shopping.service.category;

import com.example.shopping.exceptions.AlreadyExistException;
import com.example.shopping.exceptions.ResourceNotFoundException;
import com.example.shopping.model.Category;
import com.example.shopping.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class CategoryService implements  iCategoryService{
    private final  CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(
                        ()->
                        new ResourceNotFoundException("cant find category")
                );
    }
    @Override
    public Category getCategoryByName(String name) {
        return Optional.ofNullable(categoryRepository.findByName(name))
                .orElseThrow(()->{
                   return new  ResourceNotFoundException("cant find category with that name : "+ name);
                });
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new AlreadyExistException("category already exists");
        }
        return categoryRepository.save(category);
    }


    @Override
    public Category updateCategory(Category category,Long id) {
        Category oldCategory = getCategoryById(id);
        oldCategory.setName(category.getName());
        return categoryRepository.save(oldCategory);
    }

    @Override
    public void deleteCategoryById(Long id) {
        Category category=categoryRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("cant find category by id"));
        categoryRepository.delete(category);

    }

    @Override
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);

    }
}

   