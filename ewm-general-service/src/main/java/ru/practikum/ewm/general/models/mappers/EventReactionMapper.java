package ru.practikum.ewm.general.models.mappers;

import ru.practikum.ewm.general.models.EventReaction;
import ru.practikum.ewm.general.models.dto.EventReactionDto;

public class EventReactionMapper {

    public static EventReactionDto toDto(EventReaction eventReaction) {
        EventReactionDto dto = new EventReactionDto();
        if (eventReaction.getEvent() != null) {
            dto.setEventId(eventReaction.getEvent().getId());
        }
        if (eventReaction.getUser() != null) {
            dto.setUserId(eventReaction.getUser().getId());
        }
        dto.setPositive(eventReaction.isPositive());
        return dto;
    }
}
