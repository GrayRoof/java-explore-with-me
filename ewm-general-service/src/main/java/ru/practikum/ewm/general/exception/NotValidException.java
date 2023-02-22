package ru.practikum.ewm.general.exception;

public class NotValidException extends RuntimeException {
    public NotValidException(String message) {
        super(message);
    }
}
