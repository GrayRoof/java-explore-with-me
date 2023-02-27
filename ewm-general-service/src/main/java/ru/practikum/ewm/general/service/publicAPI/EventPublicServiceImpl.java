package ru.practikum.ewm.general.service.publicAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practikum.ewm.general.model.Event;
import ru.practikum.ewm.general.model.mapper.EventMapper;
import ru.practikum.ewm.general.model.SortMethod;
import ru.practikum.ewm.general.model.dto.EventFullDto;
import ru.practikum.ewm.general.repository.EventExtraFilterRepository;
import ru.practikum.ewm.general.repository.EventRepository;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        LocalDateTime start = LocalDateTime.parse(rangeStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime end = LocalDateTime.parse(rangeEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return null;
    }

    @Override
    public Event getEntity(long eventId) {

        return eventRepository.get(eventId);
    }

    @Override
    public EventFullDto get(long eventId) {

        return EventMapper.toFullDto(getEntity(eventId), 0L);
    }

    @Override
    public boolean isEventAvailable(long eventId) {
        return false;
    }
}
