package ru.skypro.homework.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.skypro.homework.exceptions.AdsAdNotFoundException;
import ru.skypro.homework.exceptions.AdsAvatarNotFoundException;
import ru.skypro.homework.exceptions.AdsCommentNotFoundException;
import ru.skypro.homework.exceptions.AdsImageFileNotFoundException;

@RestControllerAdvice
public class AdsExceptionHandler extends RuntimeException {

    @ExceptionHandler(AdsAvatarNotFoundException.class)
    public ResponseEntity<?> avatarNotFoundException(AdsAvatarNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @ExceptionHandler(AdsImageFileNotFoundException.class)
    public ResponseEntity<?> imageNotFoundException(AdsImageFileNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @ExceptionHandler(AdsAdNotFoundException.class)
    public ResponseEntity<?> adsAdNotFoundException(AdsAdNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @ExceptionHandler(AdsCommentNotFoundException.class)
    public ResponseEntity<?> adsCommentNotFoundException(AdsCommentNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

}
