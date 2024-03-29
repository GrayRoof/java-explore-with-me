package ru.practikum.ewm.general.models.dto;

import lombok.Data;
import ru.practikum.ewm.general.models.enums.EventState;

@Data
public class EventFullDto {
    String annotation;
    CategoryDto category;
    String createdOn;
    String description;
    String eventDate;
    long id;
    UserShortDto initiator;
    boolean paid;
    String title;
    LocationDto location;
    long views;
    long participantLimit;
    boolean requestModeration;
    String publishedOn;
    EventState state;
    long confirmedRequests;
    long rating;
}
