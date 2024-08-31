package ru.skypro.homework.model;

import lombok.*;
import ru.skypro.homework.enums.Role;

import javax.persistence.*;
import java.util.Objects;

/**
 * The User class represents a user entity in the database. It stores information about a user,
 * including their unique identifier (id), username, password, first name, last name, phone number, email associated image and role.
 */
@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)  //используется для хранения значений типа enum в базе данных в виде строк.
    private Role role;
    private String image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

}
