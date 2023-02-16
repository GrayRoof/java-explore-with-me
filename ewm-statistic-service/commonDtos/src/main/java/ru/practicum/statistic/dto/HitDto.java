package ru.practicum.statistic.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class HitDto {
    @NotBlank
    private String app;

    @NotBlank
    private String uri;

    @NotBlank
    private String ip;

    private String timestamp;
}
