package ru.practikum.ewm.general.service.publicAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.statistic.client.StatisticHttpClient;
import ru.practikum.ewm.general.exception.NotFoundException;
import ru.practikum.ewm.general.model.Event;
import ru.practikum.ewm.general.model.enums.EventSearchFilter;
import ru.practikum.ewm.general.model.mapper.EventMapper;
import ru.practikum.ewm.general.model.enums.SortMethod;
import ru.practikum.ewm.general.model.dto.EventFullDto;
import ru.practikum.ewm.general.repository.EventRepository;
import ru.practikum.ewm.general.service.cache.EventViewActualizer;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// TODO


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
        events.forEach(event -> {
            if (!ids.contains(event.getId())) {
                throw new NotFoundException("not exist");
            }});
        return events;
    }
}
