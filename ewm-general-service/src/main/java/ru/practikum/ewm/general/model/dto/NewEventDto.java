package ru.practikum.ewm.general.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class NewEventDto {
    @NotBlank
    String annotation;
    long category;
    String description;
    String eventDate;
    boolean paid;
    LocationDto location;
    int participantLimit;
    boolean requestModeration;
    String title;
}
