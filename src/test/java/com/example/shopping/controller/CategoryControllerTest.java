package com.example.shopping.controller;


import com.example.shopping.service.category.iCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(CategoryController.class)
@TestPropertySource(properties = "api.prefix=/api/v1")
public class CategoryControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private iCategoryService categoryService;
    @Test
    void shouldReturnCategories() throws Exception{
        mockMvc.perform(get("/api/v1/categories/all"))
                .andExpect(status().isOk());
    }
}
