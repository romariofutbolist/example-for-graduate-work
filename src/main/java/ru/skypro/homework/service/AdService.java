package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.exceptions.ImageSizeExceededException;


import java.io.IOException;

public interface AdService {
    AdsDTO findAllAds();
    ExtendedAdDTO findById(int id);
    AdsDTO getAdByAuthUser();
    AdDTO addAd(CreateOrUpdateAdDTO createOrUpdateAdDTO, MultipartFile imageFile) throws IOException, ImageSizeExceededException;
    AdDTO updateAd(int id, CreateOrUpdateAdDTO createOrUpdateAdDTO);
    void updateAdImage(int id, MultipartFile imageFile) throws IOException;
    void deleteAd(int ad);
}
