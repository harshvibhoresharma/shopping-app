package com.example.shopping.controller;

import com.example.shopping.dto.ImageDto;
import com.example.shopping.service.image.iImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockMultipartFile;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ImageController.class)
@TestPropertySource(properties="api.prefix=/api/v1")
public class ImageControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private iImageService imageService;
    @Test
    void shouldUploadImage() throws Exception {

        MockMultipartFile file =
                new MockMultipartFile(
                        "files",
                        "test.jpg",
                        "image/jpeg",
                        "test image".getBytes()
                );

        ImageDto dto = new ImageDto();
        dto.setImageId(1L);
        dto.setImageName("test.jpg");
        dto.setDownloadUrl("/api/v1/images/1");

        when(imageService.saveImage(org.mockito.ArgumentMatchers.anyList(), org.mockito.ArgumentMatchers.anyLong()))
                .thenReturn(List.of(dto));

        mockMvc.perform(
                multipart("/api/v1/images/upload")
                        .file(file)
                        .param("productId", "1")
        ).andExpect(status().isOk());
    }
}
