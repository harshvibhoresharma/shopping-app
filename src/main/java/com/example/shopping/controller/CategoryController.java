package com.example.shopping.controller;


import com.example.shopping.exceptions.AlreadyExistException;
import com.example.shopping.exceptions.ResourceNotFoundException;
import com.example.shopping.model.Category;
import com.example.shopping.response.ApiResponse;
import com.example.shopping.service.category.iCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final iCategoryService categoryService;
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories(){
        List<Category> list = categoryService.getAllCategories();
        if(list.isEmpty()){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("failed to retrieve the files",null));

        }
         return ResponseEntity.ok(new ApiResponse("successfully retrieved list",list));
    }
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@Valid @RequestBody Category category){
        try {
            categoryService.addCategory(category);
            return ResponseEntity.status(CREATED).
                    body((new ApiResponse("successfully added category 1",category)));
        } catch (AlreadyExistException e) {
            return ResponseEntity.status(CONFLICT)
                    .body(new ApiResponse("category already exits",null));
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){
        try {
            Category category =categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("successfully fetched Category",category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
        try {
            Category category = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("found successfully",category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("cant find category",null));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable Long id){
        try {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("deleted successfully",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("cant find category to delete",null));
        }
    }
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> updateById(@PathVariable Long id,@RequestBody Category category){
        try {
            Category updatedCategory = categoryService.updateCategory(category,id);
            return ResponseEntity.ok(new ApiResponse("updated successfully",updatedCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("cant find category to delete",null));
        }
    }



}
