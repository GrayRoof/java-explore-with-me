package ru.practikum.ewm.general.service.adminAPI;

import ru.practikum.ewm.general.model.EventState;
import ru.practikum.ewm.general.model.dto.EventFullDto;
import ru.practikum.ewm.general.model.dto.EventUpdateDto;

import java.util.List;

public interface EventAdminService {

    EventFullDto publish(long eventId);

    EventFullDto reject(long eventId);

    EventFullDto update(long eventId, EventUpdateDto dto);

    List<EventFullDto> findAll(List<Long> users, List<EventState> states, List<Long> categories, String rangeStart,
                               String rangeEnd, Integer from, Integer size);
}
