package ru.practikum.ewm.general.exceptions;

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
