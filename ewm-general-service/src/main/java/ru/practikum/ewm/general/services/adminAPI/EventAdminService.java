package ru.practikum.ewm.general.services.adminAPI;

import ru.practikum.ewm.general.models.Event;
import ru.practikum.ewm.general.models.enums.EventState;
import ru.practikum.ewm.general.models.dto.EventAdminUpdateDto;
import ru.practikum.ewm.general.models.dto.EventFullDto;

import java.util.Collection;
import java.util.List;

public interface EventAdminService {

    List<EventFullDto> getAll(List<Long> users, List<EventState> states, List<Long> categories, String rangeStart,
                              String rangeEnd, Integer from, Integer size);

    Collection<Event> getAllByCategory(long id);

    EventFullDto update(long eventId, EventAdminUpdateDto dto);
}
