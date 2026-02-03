package com.example.shopping.controller;

import com.example.shopping.exceptions.ResourceNotFoundException;
import com.example.shopping.model.Product;
import com.example.shopping.request.AddProductRequest;
import com.example.shopping.request.ProductUpdateRequest;
import com.example.shopping.response.ApiResponse;
import com.example.shopping.service.product.iProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final iProductService productService;
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(
                new ApiResponse("Successfully fetched products", products)
        );
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id ){
        try {
            Product product= productService.getProductById(id);
            return ResponseEntity.ok(new ApiResponse("successfully fetched ",product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest productRequest){
        try {
            Product product=productService.addProduct(productRequest);
            return ResponseEntity.ok(new ApiResponse("product added successfully",product));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long id,@RequestBody ProductUpdateRequest productUpdateRequest){
        try {
            Product product = productService.updateProduct(productUpdateRequest,id);
            return ResponseEntity.ok(new ApiResponse("successfully updated product",product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id){
        try {
            Product product=productService.getProductById(id);
            productService.deleteProductById(id);
            return ResponseEntity.ok(new ApiResponse("successfully deleted product",product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse> getProductsByCategoryName(
            @PathVariable String category) {
        List<Product> products = productService.getProductsByCategoryName(category);
        if(products.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("no products found",null));
        }
        return ResponseEntity.ok(new ApiResponse("successfully fetched products",products));
    }
    @GetMapping("/brand/{brand}")
    public ResponseEntity<ApiResponse> getProductsByBrand(
            @PathVariable String brand) {
        List<Product> products = productService.getProductsByBrand(brand);
        if(products.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("no products found",null));
        }
        return ResponseEntity.ok(new ApiResponse("successfully fetched products",products));
    }
    @GetMapping("/category/{category}/brand/{brand}")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(
            @PathVariable String category,
            @PathVariable String brand) {
        List<Product> products = productService.getProductsByCategoryAndBrand(category,brand);
        if(products.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("no products found",null));
        }
        return ResponseEntity.ok(new ApiResponse("successfully fetched products",products));
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse> getProductsByName(
            @PathVariable String name) {
        List<Product> products = productService.getProductsByName(name);
        if(products.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("no products found",null));
        }
        return ResponseEntity.ok(new ApiResponse("successfully fetched products",products));
    }
    @GetMapping("/brand/{brand}/name/{name}")
    public ResponseEntity<ApiResponse> getProductsByBrandAndName(
            @PathVariable String brand,
            @PathVariable String name) {
        List<Product> products=productService.getProductsByBrandAndName(brand,name);
        if(products.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("no products found",null));
        }
        return ResponseEntity.ok(new ApiResponse("successfully fetched products",products));
    }
    @GetMapping("/count/brand/{brand}/name/{name}")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(
            @PathVariable String brand,
            @PathVariable String name) {
        long products=productService.countProductsByBrandAndName(brand,name);
        return ResponseEntity.ok(new ApiResponse("count for products : ",products));
    }

}
