package ru.practikum.ewm.general.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practikum.ewm.general.model.dto.*;
import ru.practikum.ewm.general.service.privateAPI.EventPrivateService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Slf4j
public class EventPrivateController {

    private final EventPrivateService eventPrivateService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@Positive @PathVariable long userId, @Valid @RequestBody NewEventDto dto) {
        return eventPrivateService.createEvent(userId, dto);
    }

    @GetMapping("{eventId}")
    public EventFullDto getEventByIdWhereUserIsOwner(@Positive @PathVariable long userId,
                                                     @Positive @PathVariable long eventId) {
        return eventPrivateService.getForOwner(eventId, userId);
    }

    @GetMapping()
    public Collection<EventShortDto> findAllWhereUserIsOwner(@Positive @PathVariable long userId,
                                                             @PositiveOrZero @RequestParam(name = "from",
                                                               defaultValue = "0") Integer from,
                                                             @Positive @RequestParam(name = "size",
                                                               defaultValue = "10") Integer size) {
        return eventPrivateService.getAllForOwner(userId, from, size);
    }

    @PatchMapping("{eventId}/cancel")
    public EventFullDto cancelEvent(@Positive @PathVariable long userId, @Positive @PathVariable long eventId) {
        return eventPrivateService.cancelEvent(userId, eventId);
    }

    @PatchMapping("{eventId}")
    public EventFullDto update(@Positive @PathVariable long userId,
                               @RequestBody EventUpdateDto dto,
                               @PathVariable long eventId) {
        return eventPrivateService.update(userId, eventId, dto);
    }

    @GetMapping("{eventId}/requests")
    public Collection<RequestDto> findAllByRequesterId(@Positive @PathVariable long userId,
                                                       @Positive @PathVariable long eventId) {
        return eventPrivateService.getAllRequestsForEvent(userId, eventId);
    }

    @PatchMapping("{eventId}/requests/{reqId}/confirm")
    public RequestDto confirmRequest(@Positive @PathVariable long userId,
                                                  @Positive @PathVariable long eventId,
                                                  @Positive @PathVariable long reqId) {
        return eventPrivateService.confirmRequest(userId, eventId, reqId);
    }

    @PatchMapping("{eventId}/requests/{reqId}/reject")
    public RequestDto rejectRequest(@Positive @PathVariable long userId,
                                                 @Positive @PathVariable long eventId,
                                                 @Positive @PathVariable long reqId) {
        return eventPrivateService.rejectRequest(userId, eventId, reqId);
    }

    @PatchMapping("{eventId}/requests")
    public StatusResponseDto setStatus(@PathVariable long userId,
                                       @PathVariable long eventId,
                                       @RequestBody StatusRequestDto statusRequestDto) {
        return eventPrivateService.setStatus(userId, eventId, statusRequestDto);
    }
}
