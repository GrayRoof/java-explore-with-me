package ru.practicum.statistic.server.service;


import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
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
@AllArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class HitService {

    private final HitRepository hitRepository;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Transactional
    public HitDto add(HitDto hitDto) {
        Hit hit = HitMapper.toHit(hitDto);
        hitRepository.save(hit);
        return hitDto;
    }

    public List<StatisticViewDto> getStatistic(String start, String end, String[] uris, boolean unique) {
        LocalDateTime startDateTime = LocalDateTime.parse(start, FORMATTER);
        LocalDateTime endDateTime = LocalDateTime.parse(end, FORMATTER);
        List<StatisticView> result;
        if (!unique) {
            result = hitRepository.findNotUniqueIP(startDateTime, endDateTime, List.of(uris));
        } else {
            result = hitRepository.findUniqueIp(startDateTime, endDateTime, List.of(uris));
        }
        log.info(result.toString());
        return result.stream().map(HitMapper::toStatisticViewDto).collect(Collectors.toList());
    }
}
