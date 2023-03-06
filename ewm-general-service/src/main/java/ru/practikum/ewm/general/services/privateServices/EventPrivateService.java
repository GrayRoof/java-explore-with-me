package ru.practikum.ewm.general.services.privateServices;

import ru.practikum.ewm.general.models.dto.*;

import java.util.Collection;

public interface EventPrivateService {

    EventFullDto getForOwner(long eventId, long userId);

    Collection<EventShortDto> getAllForOwner(long userId, Integer from, Integer size);

    Collection<RequestDto> getAllRequestsForEvent(long userId, long eventId);

    EventFullDto createEvent(long userId, NewEventDto dto);

    EventFullDto update(long userId, long eventId, EventUpdateDto dto);

    StatusResponseDto setStatusToRequests(long userId, long eventId, StatusRequestDto statusRequestDto);

    void setReaction(long userId, long eventId, Boolean isPositive);

    void deleteReaction(long userId, long eventId);
}
