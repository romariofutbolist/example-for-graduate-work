package ru.skypro.homework.util;


import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UserPrincipalDTO;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.model.UserPrincipal;

import java.time.ZoneId;

import static ru.skypro.homework.util.Constant.*;

public class DTOXover {
    public static class EntityDtoFactory {

        public static Ad getAd() {
            return new Ad();
        }

        public static User getUser() {
            User user = new User();
            user.setId((long) ID1);
            return user;
        }

        public static UserPrincipalDTO getUserPrincipalDTO() {
            UserPrincipalDTO userPrincipalDTO = new UserPrincipalDTO();
            userPrincipalDTO.setId(ID1);
            userPrincipalDTO.setEmail(EMAIL);
            userPrincipalDTO.setRole(Role.USER);
            userPrincipalDTO.setPassword(PASSWORD);
            return userPrincipalDTO;
        }

        public static UserPrincipal getUserPrincipal() {
            return new UserPrincipal(getUserPrincipalDTO());
        }

        public static Comment getComment() {
            Comment comment = new Comment();
            comment.setId(ID1);
            comment.setText(COMMENT_TEXT);
            comment.setCreatedAt(LOCAL_DATE_TIME);
            comment.setUser(new User());
            comment.setAd(new Ad());
            return comment;
        }

        public static CommentDTO getCommentDto() {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setPk(ID1);
            commentDTO.setAuthor(1);
            commentDTO.setAuthorFirstName("USER1.getFirstName()");
            commentDTO.setText(COMMENT_TEXT);
            commentDTO.setCreatedAt(LOCAL_DATE_TIME.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            commentDTO.setAuthorImage(IMAGE_QUERY + commentDTO.getPk());
            return commentDTO;
        }
    }
}
