package ru.practikum.ewm.general.model.dto;

import lombok.Data;
import ru.practikum.ewm.general.model.EventStateAction;

@Data
public class EventAdminUpdateDto {
    String annotation;
    Long category;
    String description;
    String eventDate;
    Boolean paid;
    Long participantLimit;
    Boolean requestModeration;
    EventStateAction stateAction;
    String title;
}
