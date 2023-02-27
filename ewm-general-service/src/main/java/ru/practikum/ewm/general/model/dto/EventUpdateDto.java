package ru.practikum.ewm.general.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventUpdateDto {
    long eventId;
    String annotation;
    Long category;
    String description;
    String eventDate;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    String title;
}
