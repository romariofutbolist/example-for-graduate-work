package ru.skypro.homework.dto;

import lombok.Data;

/**
 * Создание ДТО
 */
@Data
public class UpdateUserDTO {
    private String firstName;
    private String lastName;
    private String phone;
}
