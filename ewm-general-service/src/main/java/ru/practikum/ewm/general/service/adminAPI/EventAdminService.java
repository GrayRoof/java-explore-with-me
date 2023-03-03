package ru.practikum.ewm.general.service.adminAPI;

import ru.practikum.ewm.general.model.Event;
import ru.practikum.ewm.general.model.enums.EventState;
import ru.practikum.ewm.general.model.dto.EventAdminUpdateDto;
import ru.practikum.ewm.general.model.dto.EventFullDto;

import java.util.Collection;
import java.util.List;

public interface EventAdminService {

    List<EventFullDto> getAll(List<Long> users, List<EventState> states, List<Long> categories, String rangeStart,
                              String rangeEnd, Integer from, Integer size);

    Collection<Event> getAllByCategory(long id);

    EventFullDto update(long eventId, EventAdminUpdateDto dto);
}
