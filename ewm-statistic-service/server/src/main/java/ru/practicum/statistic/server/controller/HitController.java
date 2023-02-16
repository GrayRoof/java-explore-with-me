package ru.practicum.statistic.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statistic.dto.HitDto;
import ru.practicum.statistic.dto.StatisticViewDto;
import ru.practicum.statistic.server.service.HitService;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
@Validated
public class HitController {

    private final HitService hitService;

    @PostMapping("/hit")
    public void add(@RequestBody HitDto hitDto) {
        log.info("StatsController POST /hit add() endpointHitDto = {} ", hitDto);
        hitService.add(hitDto);
    }

    @GetMapping("/stats")
    public List<StatisticViewDto> findStats(@RequestParam String start,
                                         @RequestParam String end,
                                         @RequestParam List<String> uris,
                                         @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("StatsController GET /stats findStats() start = {}, end = {}, uris = {}, unique ={} ",
                start, end, uris, unique);
        return hitService.getStatistic(start, end, uris, unique);
    }
}
