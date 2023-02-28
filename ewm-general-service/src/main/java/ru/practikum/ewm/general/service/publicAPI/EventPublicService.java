package ru.practikum.ewm.general.service.publicAPI;

import ru.practikum.ewm.general.model.Event;
import ru.practikum.ewm.general.model.SortMethod;
import ru.practikum.ewm.general.model.dto.EventFullDto;

import java.util.Collection;
import java.util.List;

public interface EventPublicService {

    EventFullDto get(long eventId);

    Collection<EventFullDto> getAll(String text,
                                    List<Long> categories,
                                    Boolean paid,
                                    String rangeStart,
                                    String rangeEnd,
                                    Boolean onlyAvailable,
                                    SortMethod sortMethod,
                                    Integer from,
                                    Integer size);

    Event getEntity(long eventId);

    boolean isEventAvailable(long eventId);

    Collection<Event> findAllByIdIn(Collection<Long> ids);
}
