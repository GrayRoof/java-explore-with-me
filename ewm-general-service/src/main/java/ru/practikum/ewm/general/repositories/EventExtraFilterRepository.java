package ru.practikum.ewm.general.repositories;

import ru.practikum.ewm.general.models.Event;
import ru.practikum.ewm.general.models.enums.EventSearchFilter;

import java.util.Collection;
import java.util.Map;

public interface EventExtraFilterRepository {
    Collection<Event> findAll(EventSearchFilter filter);

    void updateEventSetViews(Map<Long, Long> viewsByIds);
}
