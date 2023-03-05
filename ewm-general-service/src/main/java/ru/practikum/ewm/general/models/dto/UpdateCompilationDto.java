package ru.practikum.ewm.general.models.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateCompilationDto {
    List<Long> events;
    Boolean pinned;
    String title;
}
