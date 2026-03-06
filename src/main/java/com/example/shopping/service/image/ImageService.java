package com.example.shopping.service.image;

import com.example.shopping.dto.ImageDto;
import com.example.shopping.exceptions.ImageNotFoundException;
import com.example.shopping.exceptions.ProductNotFoundException;
import com.example.shopping.model.Image;
import com.example.shopping.model.Product;
import com.example.shopping.repository.ImageRepository;
import com.example.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements iImageService {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;


    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(()->{
                    return new ImageNotFoundException("cant find image");
                });
    }

    @Override
    public void deleteImageById(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(()->{
                    return new ImageNotFoundException("cant find image");
                });
        imageRepository.delete(image);

    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        List<ImageDto> savedImageDto = new ArrayList<>();
        Product product = productRepository.findById(productId)
                .orElseThrow(()->{
                    return new ProductNotFoundException("cant find prod with id");
                });
        for(MultipartFile file : files){
            try{
                Image image = new Image();
                image.setImageName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);
                String buildUrl="/api/v1/images/image/download";
                String downloadUrl=buildUrl+image.getImageId();
                image.setDownloadUrl(downloadUrl);
                Image saved=imageRepository.save(image);
                saved.setDownloadUrl(buildUrl+saved.getImageId());
                imageRepository.save(saved);
                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(saved.getImageId());
                imageDto.setImageName(saved.getImageName());
                imageDto.setDownloadUrl(saved.getDownloadUrl());
                savedImageDto.add(imageDto);

            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image=getImageById(imageId);
        try{
            image.setImageName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);

        } catch (SQLException | IOException e) {
            throw new RuntimeException("failed to get image",e);
        }
    }
}
