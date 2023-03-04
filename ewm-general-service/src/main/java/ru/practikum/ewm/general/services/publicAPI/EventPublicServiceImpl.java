package ru.practikum.ewm.general.services.publicAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.statistic.client.StatisticHttpClient;
import ru.practikum.ewm.general.exceptions.NotFoundException;
import ru.practikum.ewm.general.models.Event;
import ru.practikum.ewm.general.models.enums.EventSearchFilter;
import ru.practikum.ewm.general.models.mappers.EventMapper;
import ru.practikum.ewm.general.models.enums.SortMethod;
import ru.practikum.ewm.general.models.dto.EventFullDto;
import ru.practikum.ewm.general.repositories.EventRepository;
import ru.practikum.ewm.general.services.caches.EventViewActualizer;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventPublicServiceImpl implements EventPublicService {

    private final EventRepository eventRepository;
    private final EventViewActualizer actualizer;
    private final StatisticHttpClient hitClient;

    @Override
    public List<EventFullDto> getAll(String text, List<Long> categories, Boolean paid, String rangeStart,
                                     String rangeEnd, Boolean onlyAvailable, SortMethod sortMethod, Integer from,
                                     Integer size, HttpServletRequest request) {

        EventSearchFilter filter = EventSearchFilter.builder()
                .text(text)
                .categories(categories)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .paid(paid)
                .onlyAvailable(onlyAvailable)
                .sortMethod(sortMethod == null ? SortMethod.UNSUPPORTED_METHOD : sortMethod)
                .from(from)
                .size(size)
                .build();

        hitClient.addHit(request);
        return eventRepository.findAll(filter)
                .stream()
                .map(EventMapper::toFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public Event getEntity(long eventId) {
        return eventRepository.get(eventId);
    }

    @Override
    public EventFullDto get(long eventId, HttpServletRequest request) {
        Event event = getEntity(eventId);
        hitClient.addHit(request);
        actualizer.scheduleUpdating(event.getId());
        return EventMapper.toFullDto(event);
    }

    @Override
    public Collection<Event> findAllByIdIn(Collection<Long> ids) {
        Collection<Event> events = eventRepository.findAllByIdIn(ids);
        ids.forEach(id -> {
            if (events.stream().noneMatch(event -> event.getId() == id)) {
                throw new NotFoundException("Событие не найдено! id: " + id);
            }
        });
        return events;
    }
}
