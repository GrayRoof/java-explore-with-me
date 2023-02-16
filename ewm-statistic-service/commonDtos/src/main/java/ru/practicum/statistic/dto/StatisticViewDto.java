package ru.practicum.statistic.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class StatisticViewDto {
    @NotBlank
    private String app;

    @NotBlank
    private String uri;

    @NotBlank
    private long hits;
}

