package ru.skypro.homework.exceptions;

/**
 * Создание исключения для отметки, что пароль неверный
 */
public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}