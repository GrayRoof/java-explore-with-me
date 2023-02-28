package ru.practikum.ewm.general.service.adminAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practikum.ewm.general.model.EventSearchFilter;
import ru.practikum.ewm.general.model.EventState;
import ru.practikum.ewm.general.model.SortMethod;
import ru.practikum.ewm.general.model.dto.EventFullDto;
import ru.practikum.ewm.general.model.dto.EventUpdateDto;
import ru.practikum.ewm.general.model.mapper.EventMapper;
import ru.practikum.ewm.general.repository.EventExtraFilterRepository;
import ru.practikum.ewm.general.repository.EventRepository;

import java.util.List;
import java.util.stream.Collectors;

// TODO
@Service
@Slf4j
@RequiredArgsConstructor
public class EventAdminServiceImpl implements EventAdminService {

    private final EventRepository eventRepository;
    private final EventExtraFilterRepository filterRepository;

    @Override
    public EventFullDto publish(long eventId) {
        return null;
    }

    @Override
    public EventFullDto reject(long eventId) {
        return null;
    }

    @Override
    public EventFullDto update(long eventId, EventUpdateDto dto) {
        return null;
    }

    @Override
    public List<EventFullDto> findAll(List<Long> users,
                                      List<EventState> states,
                                      List<Long> categories,
                                      String rangeStart,
                                      String rangeEnd,
                                      Integer from,
                                      Integer size) {

        EventSearchFilter filter = EventSearchFilter.builder()
                .users(users)
                .states(states)
                .categories(categories)
                .rangeStart(null)
                .rangeEnd(null)
                .sortMethod(SortMethod.UNSUPPORTED_METHOD)
                .from(from)
                .size(size)
                .build();

        return filterRepository.findAll(filter).stream()
                .map(EventMapper::toFullDto)
                .collect(Collectors.toList());
    }


}
