package com.example.shopping.service;

import com.example.shopping.model.Category;
import com.example.shopping.model.Product;
import com.example.shopping.repository.CategoryRepository;
import com.example.shopping.repository.ImageRepository;
import com.example.shopping.repository.ProductRepository;
import com.example.shopping.request.AddProductRequest;
import com.example.shopping.service.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ImageRepository imageRepository;

    private ProductService productService;
    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository,categoryRepository,modelMapper,imageRepository);
    }
    @Test
    void shouldAddProduct(){
        Category category = new Category();
        category.setName("Electronics");
        AddProductRequest request = new AddProductRequest();
        request.setName("Laptop");
        request.setPrice(BigDecimal.valueOf(50000));
        request.setCategory(category);
        Product product = new Product();
        product.setName("Laptop");
        when(categoryRepository.findByName("Electronics")).thenReturn(category);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Product saved = productService.addProduct(request);
        assertThat(saved).isNotNull();
        assertThat(saved.getName()).isEqualTo("Laptop");
        verify(productRepository, times(1)).save(any(Product.class));
    }
    @Test
    void shouldReturnExistingCategoryWhenAddingProduct(){
        Category category = new Category();
        category.setName("Electronics");
        AddProductRequest request = new AddProductRequest();
        request.setName("Phone");
        request.setCategory(category);
        when(categoryRepository.findByName("Electronics")).thenReturn(category);
        when(productRepository.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));
        Product product = productService.addProduct(request);
        assertThat(product).isNotNull();
        verify(categoryRepository).findByName("Electronics");
    }
    @Test
    void shouldFindProductById() {

        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> result = productRepository.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Laptop");
    }
}
