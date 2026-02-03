package com.example.shopping.service.product;

import com.example.shopping.model.Product;
import com.example.shopping.request.AddProductRequest;
import com.example.shopping.request.ProductUpdateRequest;

import java.util.List;

public interface iProductService {
    Product addProduct(AddProductRequest request);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest Product, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategoryName(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String category,String name);
    Long countProductsByBrandAndName(String brand,String name);

}
