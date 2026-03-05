package com.example.shopping.service;

import com.example.shopping.dto.ImageDto;
import com.example.shopping.model.Image;
import com.example.shopping.model.Product;
import com.example.shopping.repository.ImageRepository;
import com.example.shopping.repository.ProductRepository;
import com.example.shopping.service.image.ImageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ImageService imageService;

    @Test
    void shouldSaveImage() {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "test.jpg",
                        "image/jpeg",
                        "test image".getBytes()
                );

        Product product = new Product();
        product.setId(1L);

        Image image = new Image();
        image.setId(1L);
        image.setFileName("test.jpg");

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(imageRepository.save(any(Image.class)))
                .thenReturn(image);

        List<ImageDto> saved = imageService.saveImage(List.of(file), 1L);

        assertThat(saved).isNotNull();
    }
}