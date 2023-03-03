package ru.practikum.ewm.general.service.cache;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.practicum.statistic.client.StatisticHttpClient;
import ru.practikum.ewm.general.repository.EventRepository;

import javax.transaction.Transactional;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventViewActualizer {

    private final static int LIMIT_TO_UPDATE = 200;
    private final StatisticHttpClient statisticHttpClient;
    private final EventRepository eventRepository;

    private final Set<Long> idsToUpdate = new HashSet<>();

    @Scheduled(fixedDelay = 10000)
    @Transactional
    public void updateCachedViews() {
        List<Long> idsToUpdateByLimit = getIdsToUpdateByLimit();
        if (idsToUpdateByLimit.isEmpty()) {
            log.info("CACHE: nothing to update");
            return;
        }

        EventViewsCache viewsCache = new EventViewsCache(statisticHttpClient, idsToUpdateByLimit);
        Map<Long, Long> viewsByIds = new HashMap<>();
        for (long eventId : idsToUpdateByLimit) {
            Long views = viewsCache.getOrNull(eventId);
            if (views != null) {
                viewsByIds.put(eventId, views);
            }
        }

        eventRepository.updateEventSetViews(viewsByIds);
    }

    public void scheduleUpdating(long eventId) {
        synchronized (idsToUpdate) {
            idsToUpdate.add(eventId);
            log.info("CACHE: idsToUpdate: {}", idsToUpdate);
        }
    }

    private List<Long> getIdsToUpdateByLimit() {
        List<Long> result = new ArrayList<>();

        synchronized (idsToUpdate) {
            Iterator<Long> ids = idsToUpdate.iterator();
            while (ids.hasNext() && result.size() < LIMIT_TO_UPDATE) {
                long id = ids.next();
                result.add(id);
                ids.remove();
            }
        }

        return result;
    }

}
