package ru.practikum.ewm.general.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practikum.ewm.general.models.enums.EventReactionAction;

@Data
@NoArgsConstructor
public class EventReactionDto {
    EventReactionAction action;
}
