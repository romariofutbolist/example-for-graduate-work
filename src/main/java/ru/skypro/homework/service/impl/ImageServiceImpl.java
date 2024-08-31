package ru.skypro.homework.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.exceptions.AdsImageFileNotFoundException;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.repository.ImageRepo;
import ru.skypro.homework.service.ImageService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;


@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepo imageRepo;
    @Value("${path.to.images.folder}")
    private String imagesFolder;

    @Override
    public Image createImage(Ad ad, MultipartFile image) throws IOException {
        final Path filePath = Path.of(
                imagesFolder,
                ad.getPk() + "." + getExtension(image.getOriginalFilename())
        );

        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = image.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }

        final Image image1 = imageRepo.findById(ad.getPk())
                .orElseGet(Image::new);
        image1.setPath(filePath.toString());
        image1.setFileSize(image.getSize());
        image1.setMediaType(image.getContentType());
        image1.setAd(ad);

        return imageRepo.save(image1);
    }

    @Override
    public Image saveAdImage(Image image) {
        return imageRepo.save(image);
    }

    @Override
    public void getAdImage(Integer id, HttpServletResponse response) throws IOException {
        Ad ad = Ad.builder()
                .pk(id)
                .build();
        Image adImage = imageRepo.findByAd(ad)
                .orElseThrow(() -> new AdsImageFileNotFoundException("Image of ad with id " + id + " not found"));
        Path path = Path.of(adImage.getPath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(adImage.getMediaType());
            response.setContentLength((int) adImage.getFileSize());
            is.transferTo(os);
        }
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
