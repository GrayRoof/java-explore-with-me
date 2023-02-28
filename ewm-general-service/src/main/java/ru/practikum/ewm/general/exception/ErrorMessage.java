package ru.practikum.ewm.general.exception;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Date;

@AllArgsConstructor
@Value
public class ErrorMessage {
    Date timestamp;
    int status;
    String error;
    String path;
}
