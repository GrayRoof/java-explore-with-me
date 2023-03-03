package ru.practikum.ewm.general.repository;

import ru.practikum.ewm.general.model.Event;
import ru.practikum.ewm.general.model.enums.EventSearchFilter;

import java.util.Collection;
import java.util.Map;

public interface EventExtraFilterRepository {
    Collection<Event> findAll(EventSearchFilter filter);

    void updateEventSetViews(Map<Long, Long> viewsByIds);
}
