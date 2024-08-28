package ru.skypro.homework.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.exceptions.AdNotFoundException;
import ru.skypro.homework.exceptions.CommentNotFoundException;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.repository.AdRepo;
import ru.skypro.homework.repository.CommentRepo;
import ru.skypro.homework.service.WebSecurityService;

@Service
@RequiredArgsConstructor
public class WebSecurityServiceImpl implements WebSecurityService {
    private final AdRepo adRepo;
    private final CommentRepo commentRepo;
    @Override
    public boolean canAccessInAd(Integer id, String username) {
        Ad ad = adRepo.findById(id).orElseThrow(() -> new AdNotFoundException("Ad not found"));
        return ad.getUser().getEmail().equals(username);
    }

    @Override
    public boolean canAccessInComment(Integer id, String username) {
        Comment comment = commentRepo.findById(id).orElseThrow(() -> new CommentNotFoundException("Comment not found"));
        return comment.getUser().getEmail().equals(username);
    }
}
