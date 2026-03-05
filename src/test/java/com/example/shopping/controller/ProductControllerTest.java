package com.example.shopping.controller;
import com.example.shopping.service.product.ProductService;
import com.example.shopping.service.product.iProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
@WebMvcTest(ProductController.class)
class ProductControllerTest {
        @Autowired
        private MockMvc mockMvc;
        @MockBean
        private iProductService productService;

        @Test
        void shouldReturnProducts() throws Exception {

            mockMvc.perform(get("/api/v1/products/all"))
                    .andExpect(status().isOk());
        }
}
