package ru.practicum.statistic.server.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StatisticView {
    private String app;
    private String uri;
    private long hits;
}
