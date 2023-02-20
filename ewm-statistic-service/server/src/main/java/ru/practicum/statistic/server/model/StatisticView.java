package ru.practicum.statistic.server.model;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class StatisticView {
    String app;
    String uri;
    long hits;
}
