package ru.practikum.ewm.general.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NewCompilationDto {
    List<Long> events;
    Boolean pinned;
    String title;
}
