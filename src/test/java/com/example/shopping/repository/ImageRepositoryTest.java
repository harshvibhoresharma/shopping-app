package com.example.shopping.repository;

import com.example.shopping.model.Image;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ImageRepositoryTest {

    @Autowired
    private ImageRepository imageRepository;

    @Test
    @DisplayName("should save image")
    void shouldSaveImage() {

        Image image = new Image();
        image.setImageName("test.jpg");
        image.setFileType("image/jpeg");

        Image saved = imageRepository.save(image);

        assertThat(saved).isNotNull();
        assertThat(saved.getImageId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("should find image by id")
    void shouldFindImageById() {

        Image image = new Image();
        image.setImageName("test.jpg");
        image.setFileType("image/jpeg");

        Image saved = imageRepository.save(image);

        Optional<Image> found = imageRepository.findById(saved.getImageId());

        assertThat(found).isPresent();
        assertThat(found.get().getImageName()).isEqualTo("test.jpg");
    }
}