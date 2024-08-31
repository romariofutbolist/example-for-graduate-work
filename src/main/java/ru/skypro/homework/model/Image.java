package ru.skypro.homework.model;


import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "images")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String path;
    @Column(name = "file_size")
    private long fileSize;
    @Column(name = "media_type")
    private String mediaType;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ad_id")
    private Ad ad;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image adImage = (Image) o;
        return fileSize == adImage.fileSize
                && Objects.equals(path, adImage.path)
                && Objects.equals(mediaType, adImage.mediaType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, fileSize, mediaType);
    }
}
