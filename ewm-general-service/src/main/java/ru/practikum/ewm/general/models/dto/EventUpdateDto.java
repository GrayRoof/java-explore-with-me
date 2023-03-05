package ru.practikum.ewm.general.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practikum.ewm.general.models.enums.EventStateAction;

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
    EventStateAction stateAction;
}
