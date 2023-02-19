package ru.practicum.statistic.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class StatisticViewDto {
    @NotBlank
    String app;

    @NotBlank
    String uri;

    long hits;
}

