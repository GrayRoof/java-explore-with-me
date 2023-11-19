package ru.practikum.ewm.general.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventReactionDto {
    long eventId;
    long userId;
    boolean isPositive;
}
