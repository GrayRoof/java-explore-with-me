package ru.practicum.statistic.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class HitDto {
    @NotBlank
    String app;

    @NotBlank
    String uri;

    @NotBlank
    String ip;

    String timestamp;
}
