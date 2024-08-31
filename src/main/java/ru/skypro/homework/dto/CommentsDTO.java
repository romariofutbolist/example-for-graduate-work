package ru.skypro.homework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Создание ДТО
 */
@Data
@Builder
@Schema(name = "Comments")
public class CommentsDTO {

    private Integer count;
    private List<CommentDTO> results;
}
