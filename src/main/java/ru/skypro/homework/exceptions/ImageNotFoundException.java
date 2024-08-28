package ru.skypro.homework.exceptions;


/**
 * Создание исключения для отметки, что изображение не найдено
 */
public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException(String message) {
        super(message);
    }
}