package com.example.shopping.repository;

import com.example.shopping.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Test
    void shouldSave(){
        Product product = new Product();
        product.setName("Laptop");
        product.setPrice(BigDecimal.valueOf(50000));
        Product saved = productRepository.save(product);
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isGreaterThan(0);
        assertThat(saved.getName()).isEqualTo("Laptop");
    }

}
