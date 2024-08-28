package ru.skypro.homework.exceptions;

/**
 * Создание исключения для отметки, что объявление не найдено
 */
public class AdNotFoundException extends RuntimeException {
    public AdNotFoundException(String message) {
        super(message);
    }
}
