package ru.practikum.ewm.general.service.publicAPI;

import ru.practikum.ewm.general.model.SortMethod;
import ru.practikum.ewm.general.model.dto.EventFullDto;

import java.util.Collection;
import java.util.List;

public interface EventPublicService {

    Collection<EventFullDto> findAll(String text,
                                     List<Long> categories,
                                     Boolean paid,
                                     String rangeStart,
                                     String rangeEnd,
                                     Boolean onlyAvailable,
                                     SortMethod sortMethod,
                                     Integer from,
                                     Integer size);

    EventFullDto findById(long eventId);
}
