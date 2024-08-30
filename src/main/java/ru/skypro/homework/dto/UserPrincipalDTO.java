package ru.skypro.homework.dto;

import lombok.Data;

/**
 * Создание ДТО
 */
@Data
public class UserPrincipalDTO {

    private Integer id;
    private String email;
    private String password;
    private Role role;

    public UserPrincipalDTO(Integer id, String email, String password, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserPrincipalDTO() {
    }
}
