package ru.practikum.ewm.general.models.dto;

import lombok.Data;

@Data
public class EventShortDto {
    String annotation;
    CategoryDto category;
    String eventDate;
    long id;
    UserShortDto initiator;
    boolean paid;
    String title;
    long views;
    long confirmedRequests;
}
