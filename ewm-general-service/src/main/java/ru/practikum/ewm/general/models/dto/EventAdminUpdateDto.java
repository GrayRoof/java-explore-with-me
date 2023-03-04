package ru.practikum.ewm.general.models.dto;

import lombok.Data;
import ru.practikum.ewm.general.models.enums.EventStateAction;

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
