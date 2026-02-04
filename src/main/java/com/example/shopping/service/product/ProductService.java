package com.example.shopping.service.product;

import com.example.shopping.exceptions.ProductNotFoundException;
import com.example.shopping.exceptions.ResourceNotFoundException;
import com.example.shopping.model.Category;
import com.example.shopping.model.Product;
import com.example.shopping.repository.CategoryRepository;
import com.example.shopping.repository.ProductRepository;
import com.example.shopping.request.AddProductRequest;
import com.example.shopping.request.ProductUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements iProductService{
    final private ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    public ProductService(ProductRepository productRepository,CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
        this.productRepository=productRepository;
    }
    @Override
    public Product addProduct(AddProductRequest request) {
        Category reqCategory = request.getCategory();
        Category category = categoryRepository.findByName(reqCategory.getName());
        if(category==null){
            categoryRepository.save(new Category(reqCategory.getName()));
        }
        return productRepository.save(createProduct(request,category));
    }
    private static Product createProduct(AddProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("unable to find product with id : "+id)
                );
    }

    @Override
    public void deleteProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("product not found"));
        product.setCategory(null);
        productRepository.delete(product);
    }
    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct->updateExistingProduct(existingProduct,request))
                .map(productRepository :: save)
                .orElseThrow(()->new ResourceNotFoundException("cant find product"));
    }
    private Product updateExistingProduct(Product product, ProductUpdateRequest request){
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setInventory(request.getInventory());
        Category reqCategory = request.getCategory();
        if(reqCategory!=null){
            String categoryName = reqCategory.getName();
            Category category = categoryRepository.findByName(categoryName);
            if(category==null){
                category = categoryRepository.save(new Category(categoryName));
            }
            product.setCategory(category);

        }
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategoryName(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {

        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {

        return productRepository.findByName(name);
    }
    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }
    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }
}

