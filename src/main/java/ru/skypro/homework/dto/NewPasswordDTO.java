package ru.skypro.homework.dto;

import lombok.Data;

/**
 * Создание ДТО
 */
@Data
public class NewPasswordDTO {
    private String currentPassword;
    private String newPassword;
}