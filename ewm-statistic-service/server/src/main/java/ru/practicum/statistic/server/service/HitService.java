package ru.practicum.statistic.server.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.statistic.dto.HitDto;
import ru.practicum.statistic.dto.StatisticViewDto;
import ru.practicum.statistic.server.model.Hit;
import ru.practicum.statistic.server.model.HitMapper;
import ru.practicum.statistic.server.model.StatisticView;
import ru.practicum.statistic.server.repository.HitRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HitService {

    private final HitRepository hitRepository;

    public void add(HitDto hitDto) {
        Hit hit = HitMapper.toHit(hitDto);
        hitRepository.save(hit);
    }

    public List<StatisticViewDto> getStatistic(String start, String end, List<String> uris, boolean unique) {
        LocalDateTime startDateTime = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endDateTime = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<StatisticView> result;
        if (!unique) {
            result = hitRepository.findNotUniqueIP(startDateTime, endDateTime, uris);
        } else {
            result = hitRepository.findUniqueIp(startDateTime, endDateTime, uris);
        }

        return result.stream().map(HitMapper::toStatisticViewDto).collect(Collectors.toList());
    }
}
