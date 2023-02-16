package ru.practicum.statistic.server.model;

import ru.practicum.statistic.dto.HitDto;
import ru.practicum.statistic.dto.StatisticViewDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HitMapper {
    public static Hit toHit(HitDto hitDto) {
        Hit hit = new Hit();
        hit.setApp(hitDto.getApp());
        hit.setIp(hitDto.getIp());
        hit.setUri(hitDto.getUri());
        hit.setTimestamp(LocalDateTime.parse(
                hitDto.getTimestamp(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        return hit;
    }

    public static StatisticViewDto toStatisticViewDto(StatisticView statisticView) {
        StatisticViewDto statisticViewDto = new StatisticViewDto();
        statisticViewDto.setApp(statisticView.getApp());
        statisticViewDto.setUri(statisticView.getUri());
        statisticViewDto.setHits(statisticView.getHits());
        return statisticViewDto;
    }
}
