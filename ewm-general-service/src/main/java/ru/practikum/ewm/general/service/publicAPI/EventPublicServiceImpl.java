package ru.practikum.ewm.general.service.publicAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practikum.ewm.general.exception.NotFoundException;
import ru.practikum.ewm.general.model.Event;
import ru.practikum.ewm.general.model.EventSearchFilter;
import ru.practikum.ewm.general.model.mapper.EventMapper;
import ru.practikum.ewm.general.model.SortMethod;
import ru.practikum.ewm.general.model.dto.EventFullDto;
import ru.practikum.ewm.general.repository.EventExtraFilterRepository;
import ru.practikum.ewm.general.repository.EventRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// TODO


@Service
@Slf4j
@RequiredArgsConstructor
public class EventPublicServiceImpl implements EventPublicService {

    private final EventRepository eventRepository;
    private final EventExtraFilterRepository filterRepository;

    @Override
    public List<EventFullDto> getAll(String text, List<Long> categories, Boolean paid, String rangeStart,
                                     String rangeEnd, Boolean onlyAvailable, SortMethod sortMethod, Integer from,
                                     Integer size) {

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

        return filterRepository.findAll(filter)
                .stream()
                .map(EventMapper::toFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public Event getEntity(long eventId) {
        return eventRepository.get(eventId);
    }

    @Override
    public EventFullDto get(long eventId) {
        return EventMapper.toFullDto(getEntity(eventId));
    }

    @Override
    public boolean isEventAvailable(long eventId) {
        Event event = getEntity(eventId);
        return (event.getParticipantLimit() - event.getConfirmedRequests() > 0) ||
                event.getParticipantLimit() == 0;
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
