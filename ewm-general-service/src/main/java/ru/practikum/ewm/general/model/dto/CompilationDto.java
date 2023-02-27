package ru.practikum.ewm.general.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class CompilationDto {
    long id;
    boolean pinned;
    String title;
    List<EventShortDto> events;
}
