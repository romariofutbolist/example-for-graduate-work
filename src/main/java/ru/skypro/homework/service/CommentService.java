package ru.skypro.homework.service;

import org.springframework.http.ResponseEntity;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;


public interface CommentService {

    //    CommentsDTO getAllByCommentsById(int id);
    CommentsDTO getAllByCommentById(int id);

    CommentDTO createComment(int adId, CreateOrUpdateCommentDTO text);

    //    CommentDTO findById(int commentId);
    void deleteComment(int adId, int commentId);
    CommentDTO updateComment(int adId, int commentId, CreateOrUpdateCommentDTO text);
}
