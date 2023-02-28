package ru.practikum.ewm.general.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class EventSearchFilter {
    String text;
    List<Long> categories;
    List<Long> users;
    List<EventState> states;
    Boolean paid;
    String rangeStart;
    String rangeEnd;
    boolean onlyAvailable;
    SortMethod sortMethod;
    int from;
    int size;
}
