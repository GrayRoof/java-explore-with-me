package ru.practikum.ewm.general.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewEventDto {
    String annotation;
    long category;
    String description;
    String eventDate;
    boolean paid;
    int participantLimit;
    boolean requestModeration;
    String title;
}
