package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CommentsDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.exceptions.AdNotFoundException;
import ru.skypro.homework.exceptions.CommentNotFoundException;
import ru.skypro.homework.exceptions.UserNotFoundException;
import ru.skypro.homework.mappers.CommentMapper;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepo;
import ru.skypro.homework.repository.CommentRepo;
import ru.skypro.homework.repository.UserRepo;
import ru.skypro.homework.service.CommentService;


import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;
    private final AdRepo adRepo;
    private final UserRepo userRepo;
    private final CommentMapper commentMapper;
    private final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    /**
     * Возвращет все комментарии для конкретного объявления
     *
     * @param id Id объявления
     * @return CommentsDTO
     * @see CommentsDTO
     */
    @Override
    public CommentsDTO getAllByCommentById(int id) {
        logger.info("Comment getAllByCommentById is running");
//        commentRepo.count();
        return commentMapper.toCommentsDTO(commentRepo.getAllByAdId(id));
    }

    /**
     * Добавление нового комментария к объявлению
     *
     * @param adId Id объявления
     * @param createOrUpdateCommentDTO DTO объект, представляющий новый комментарий
     * @return CommentDTO нового комментария
     * @throws AdNotFoundException, если объявления с переданным Id не существует
     */
    @Override
    public CommentDTO createComment(int adId, CreateOrUpdateCommentDTO createOrUpdateCommentDTO) {
        logger.info("Comment createComment is running");
        Comment newComment = new Comment();
        newComment.setCreatedAt(LocalDateTime.now());
        newComment.setText(createOrUpdateCommentDTO.getText());
        newComment.setAd(adRepo.findById(adId)
                .orElseThrow(() -> new AdNotFoundException("Объявление с Id: " + adId + " не найдено")));
        newComment.setUser(getCurrentUser());
        return commentMapper.toDto(commentRepo.save(newComment));
    }

    /**
     * Удаление ранее опубликованного комментария, принадлежащего текущему пользователю
     *
     * @param adId Id объявления
     * @param commentId Id комментария
     */
    @PreAuthorize("@webSecurityServiceImpl.canAccessInComment(#id, principal.username) or hasRole('ADMIN')")
    @Override
    public void deleteComment(int adId, int commentId) {
        logger.info("Comment deleteComment is running");
        commentRepo.deleteById(Math.toIntExact(commentId));
    }

    /**
     * Обновление текста комментария пользователя
     *
     * @param adId id объявления, к которому был опубликован комментарий
     * @param commentId id комментария
     * @param text DTO объект, представляющий обновленный комментарий
     * @return обновленный CommentDTO
     */
    @PreAuthorize("@webSecurityServiceImpl.canAccessInComment(#id, principal.username) or hasRole('ADMIN')")
    @Override
    public CommentDTO updateComment(int adId,
                                    int commentId,
                                    CreateOrUpdateCommentDTO text) {
        logger.info("Comment updateComment is running");
        Comment result = commentRepo.findById(commentId).orElse(null);
        assert result != null;
        result.setText(text.getText());
        result.setCreatedAt(LocalDateTime.now());
        return commentMapper.toDto(commentRepo.save(result));
    }

    /**
     * Метод, который возвращает текущего пользователя
     * @return User
     * @see User
     */
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserDetails) ((org.springframework.security.core.Authentication) authentication).getPrincipal()).getUsername();
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email: " + email + " is not found"));
    }

    private void checkIfCommentExist(int commentId) {
        if (!commentRepo.existsById(commentId)) {
            throw new CommentNotFoundException("Комментарий с id: " + commentId + " не найден");
        }
    }
}
