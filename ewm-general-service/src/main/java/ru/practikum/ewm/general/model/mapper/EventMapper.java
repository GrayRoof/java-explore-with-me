package ru.practikum.ewm.general.model.mapper;

import ru.practikum.ewm.general.model.Category;
import ru.practikum.ewm.general.model.Event;
import ru.practikum.ewm.general.model.EventState;
import ru.practikum.ewm.general.model.User;
import ru.practikum.ewm.general.model.dto.EventFullDto;
import ru.practikum.ewm.general.model.dto.EventShortDto;
import ru.practikum.ewm.general.model.dto.NewEventDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventMapper {

    public static Event toEvent(NewEventDto newEventDto, User initiator, Category category) {

        Event event = new Event();
        event.setAnnotation(newEventDto.getAnnotation());
        event.setCategory(category);
        event.setCreatedOn(LocalDateTime.now());
        event.setEventDate(LocalDateTime.parse(
                newEventDto.getEventDate(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        event.setDescription(newEventDto.getDescription());
        event.setInitiator(initiator);
        event.setTitle(newEventDto.getTitle());
        event.setPaid(newEventDto.isPaid());
        event.setParticipantLimit(newEventDto.getParticipantLimit());
        event.setRequestModeration(newEventDto.isRequestModeration());
        event.setViews(0L);
        event.setState(EventState.PENDING);

        return event;
    }

    public static EventFullDto toFullDto(Event event) {

        EventFullDto eventFullDto = new EventFullDto();
        eventFullDto.setAnnotation(event.getAnnotation());
        eventFullDto.setCategory(CategoryMapper.toDto(event.getCategory()));
        eventFullDto.setCreatedOn(event.getCreatedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        eventFullDto.setEventDate(event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        eventFullDto.setDescription(event.getDescription());
        eventFullDto.setTitle(event.getTitle());
        eventFullDto.setPaid(event.isPaid());
        eventFullDto.setParticipantLimit(event.getParticipantLimit());
        eventFullDto.setRequestModeration(event.isRequestModeration());
        eventFullDto.setViews(event.getViews());
        eventFullDto.setPublishedOn(event.getPublishedOn() != null ?
                event.getPublishedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null);
        eventFullDto.setId(event.getId());
        eventFullDto.setInitiator(UserMapper.toShortDto(event.getInitiator()));
        eventFullDto.setState(event.getState());
        eventFullDto.setConfirmedRequests(event.getConfirmedRequests());

        return eventFullDto;
    }

    public static EventShortDto toShortDto(Event event) {

        EventShortDto eventShortDto = new EventShortDto();
        eventShortDto.setAnnotation(event.getAnnotation());
        eventShortDto.setCategory(CategoryMapper.toDto(event.getCategory()));
        eventShortDto.setEventDate(event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        eventShortDto.setTitle(event.getTitle());
        eventShortDto.setPaid(event.isPaid());
        eventShortDto.setViews(event.getViews());
        eventShortDto.setId(event.getId());
        eventShortDto.setInitiator(UserMapper.toShortDto(event.getInitiator()));
        eventShortDto.setConfirmedRequests(event.getConfirmedRequests());
        return eventShortDto;
    }
}