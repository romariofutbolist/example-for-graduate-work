package ru.skypro.homework.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.model.Ad;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        imports = {
                ru.skypro.homework.model.User.class,
                ru.skypro.homework.model.Ad.class
        }
)
public interface AdMapper {

    @Mappings(value = {
            @Mapping(target = "author", expression = "java(ad.getUser().getId())"),
            @Mapping(target = "image", expression = """
                    java("/images/" + ad.getPk())
                    """)
    })
    AdDTO adToAdDTO(Ad ad);

    Ad createOrUpdateAdDTOToAd(CreateOrUpdateAdDTO adDTO);

    @Mappings(value = {
            @Mapping(target = "authorFirstName", expression = "java(ad.getUser().getFirstName())"),
            @Mapping(target = "authorLastName", expression = "java(ad.getUser().getLastName())"),
            @Mapping(target = "email", expression = "java(ad.getUser().getEmail())"),
            @Mapping(target = "image", expression = """
                    java("/images/" + ad.getPk())
                    """)
    })
    ExtendedAdDTO adToExtendedAdDTO(Ad ad);

}
