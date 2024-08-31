package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.enums.Role;

/**
 * Создание ДТО
 */
@Data
public class UserDTO {

    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
    private String image;

}
