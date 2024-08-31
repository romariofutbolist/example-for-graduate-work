package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Image;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public interface ImageService {

    Image createImage(Ad ad, MultipartFile image) throws IOException;

    Image saveAdImage(Image image);

    void getAdImage(Integer id, HttpServletResponse response) throws IOException;
}
