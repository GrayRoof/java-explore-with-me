package ru.practikum.ewm.general.service.adminAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practikum.ewm.general.model.EventState;
import ru.practikum.ewm.general.model.dto.EventFullDto;
import ru.practikum.ewm.general.model.dto.EventUpdateDto;
import ru.practikum.ewm.general.repository.EventRepository;

import java.util.List;

// TODO
@Service
@Slf4j
@RequiredArgsConstructor
public class EventAdminServiceImpl implements EventAdminService {

    private final EventRepository eventRepository;

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
        return null;
    }
}
