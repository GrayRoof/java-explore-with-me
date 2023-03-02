package ru.practikum.ewm.general.service.privateAPI;

import ru.practikum.ewm.general.model.dto.*;

import java.util.Collection;

public interface EventPrivateService {

    EventFullDto getForOwner(long eventId, long userId);

    Collection<EventShortDto> getAllForOwner(long userId, Integer from, Integer size);

    Collection<RequestDto> getAllRequestsForEvent(long userId, long eventId);

    EventFullDto createEvent(long userId, NewEventDto dto);

    EventFullDto update(long userId, long eventId, EventUpdateDto dto);

    EventFullDto cancelEvent(long eventId, long userId);

    StatusResponseDto setStatusToRequests(long userId, long eventId, StatusRequestDto statusRequestDto);
}
