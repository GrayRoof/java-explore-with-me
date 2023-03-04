package ru.practikum.ewm.general.services.publicAPI;

import ru.practikum.ewm.general.models.Event;
import ru.practikum.ewm.general.models.enums.SortMethod;
import ru.practikum.ewm.general.models.dto.EventFullDto;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

public interface EventPublicService {

    EventFullDto get(long eventId, HttpServletRequest request);

    Collection<EventFullDto> getAll(String text,
                                    List<Long> categories,
                                    Boolean paid,
                                    String rangeStart,
                                    String rangeEnd,
                                    Boolean onlyAvailable,
                                    SortMethod sortMethod,
                                    Integer from,
                                    Integer size,
                                    HttpServletRequest request);

    Event getEntity(long eventId);

    Collection<Event> findAllByIdIn(Collection<Long> ids);
}
