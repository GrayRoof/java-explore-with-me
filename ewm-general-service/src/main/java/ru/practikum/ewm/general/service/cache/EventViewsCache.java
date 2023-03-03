package ru.practikum.ewm.general.service.cache;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.statistic.client.StatisticHttpClient;
import ru.practicum.statistic.dto.StatisticViewDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class EventViewsCache {
    private static final String EVENT_URI = "/events/";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd%20HH:mm:ss");

    private static final LocalDateTime MIN_DATE = LocalDateTime.now().minusYears(1);
    private static final LocalDateTime MAX_DATE = LocalDateTime.now().plusDays(1);

    private final Map<String, Long> viewsByUri;

    public EventViewsCache(StatisticHttpClient client, List<Long> ids) {
        List<String> uris = ids
                .stream()
                .map(EventViewsCache::getEventUri)
                .collect(Collectors.toList());

        viewsByUri = client.getStatistic(
                FORMATTER.format(MIN_DATE),
                FORMATTER.format(MAX_DATE),
                uris,
                true
        ).stream().collect(Collectors.toMap(StatisticViewDto::getUri, StatisticViewDto::getHits));
    }

    private static String getEventUri(long eventId) {
        return EVENT_URI + eventId;
    }

    public Long getOrNull(long eventId) {
        log.info("CACHE: viewsByUri: {}", viewsByUri);
        return viewsByUri.getOrDefault(getEventUri(eventId), null);
    }
}
