package ru.skypro.homework.mappers;



import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.model.Comment;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        imports = {java.util.Date.class, ru.skypro.homework.model.User.class}
)
public interface CommentMapper {

    @Mappings(value = {
            @Mapping(target = "author", expression = "java(comment.getUser().getId())"),
            @Mapping(target = "authorImage", expression = "java(comment.getUser().getImage())"),
            @Mapping(target = "authorFirstName", expression = "java(comment.getUser().getFirstName())"),
            @Mapping(target = "createdAt", expression = "java(comment.getCreatedAt().getTime())")
    })
    CommentDTO commentToCommentDTO(Comment comment);

    @Mappings(value = @Mapping(target = "createdAt", expression = "java(new Date())"))
    Comment createOrUpdateCommentDTOToComment(CreateOrUpdateCommentDTO commentDTO);

}
