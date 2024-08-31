package ru.skypro.homework.model;


import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Создание сущности Comment
 */
@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder  //Аннотация @Builder в Lombok позволяет создавать объекты более лаконичным и выразительным способом, делая код более читаемым и поддерживаемым.
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;
    @Column(name = "created_at")
    private Date createdAt;
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ad_id")
    private Ad ad;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(text, comment.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "pk=" + pk +
                ", createdAt=" + createdAt +
                ", text='" + text + '\'' +
                ", user=" + user +
                ", ad=" + ad +
                '}';
    }
}
