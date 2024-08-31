package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.enums.Role;
import ru.skypro.homework.exceptions.AdsCommentNotFoundException;
import ru.skypro.homework.mappers.CommentMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.CommentRepo;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.UserService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepository;
    private final AdService adService;
    private final UserService userService;
    private final CommentMapper commentMapper;

    @Override
    public List<Comment> getAllCommentsByAdId(Integer id) {
        Ad ad = adService.getAdById(id);

        return commentRepository.findAllByAd(ad);
    }

    @Override
    public Comment addCommentToAdByItsId(Integer id, String username, CreateOrUpdateCommentDTO commentDTO) {
        Ad ad = adService.getAdById(id);
        User user = userService.findUserByEmail(username);

        Comment newComment = commentMapper.createOrUpdateCommentDTOToComment(commentDTO);
        newComment.setUser(user);
        newComment.setAd(ad);

        return commentRepository.save(newComment);
    }

    @Override
    @Transactional
    public HttpStatus deleteAdCommentByItsId(Integer adId, Integer commentId, String username) {
        Ad foundAd = adService.getAdById(adId);
        Comment foundComment = findCommentById(commentId);

        if (!foundComment.getAd().equals(foundAd)) {
            return HttpStatus.NOT_FOUND;
        }

        User foundUser = userService.findUserByEmail(username);
        Role userRole = foundUser.getRole();
        boolean isCommentAuthor = (foundComment.getUser() == foundUser);
        boolean userHasPermit = (isCommentAuthor || userRole == Role.ADMIN);

        if (userHasPermit) {
            commentRepository.delete(commentId);
            return HttpStatus.OK;
        } else {
            return HttpStatus.FORBIDDEN;
        }
    }

    @Override
    public ResponseEntity<CommentDTO> updateAdCommentByItsId(
            Integer adId,
            Integer commentId,
            CreateOrUpdateCommentDTO commentDTO,
            String username
    ) {
        Ad foundAd = adService.getAdById(adId);
        Comment foundComment = findCommentById(commentId);

        if (!foundComment.getAd().equals(foundAd)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        User foundUser = userService.findUserByEmail(username);
        Role userRole = foundUser.getRole();
        boolean isCommentAuthor = (foundComment.getUser() == foundUser);
        boolean userHasPermit = (isCommentAuthor || userRole == Role.ADMIN);

        if (userHasPermit) {
            Comment updateComment = commentMapper.createOrUpdateCommentDTOToComment(commentDTO);

            foundComment.setText(updateComment.getText());
            foundComment.setUser(foundUser);
            foundComment.setCreatedAt(updateComment.getCreatedAt());

            Comment savedComment = commentRepository.save(foundComment);

            return ResponseEntity.ok(commentMapper.commentToCommentDTO(savedComment));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    private Comment findCommentById(Integer id) {
        return commentRepository.findByPk(id)
                .orElseThrow(() -> new AdsCommentNotFoundException("Comment with id " + id + " not found."));
    }

}
