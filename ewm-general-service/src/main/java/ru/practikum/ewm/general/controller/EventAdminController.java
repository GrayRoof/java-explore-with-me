package ru.practikum.ewm.general.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practikum.ewm.general.model.EventState;
import ru.practikum.ewm.general.model.dto.EventFullDto;
import ru.practikum.ewm.general.model.dto.EventUpdateDto;
import ru.practikum.ewm.general.service.adminAPI.EventAdminService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
public class EventAdminController {

    private final EventAdminService eventAdminService;

    @PatchMapping("{eventId}/publish")
    public EventFullDto publish(@Positive @PathVariable long eventId) {
        return eventAdminService.publish(eventId);
    }

    @PatchMapping("{eventId}/reject")
    public EventFullDto reject(@Positive @PathVariable long eventId) {
        return eventAdminService.reject(eventId);
    }

    @PutMapping("/{eventId}")
    public EventFullDto update(@Positive @PathVariable long eventId, @RequestBody EventUpdateDto dto) {
        return eventAdminService.update(eventId, dto);
    }

    @GetMapping
    public Collection<EventFullDto> findAll(@RequestParam(required = false) List<Long> users,
                                            @RequestParam(required = false) List<EventState> states,
                                            @RequestParam(required = false) List<Long> categories,
                                            @RequestParam(required = false) String rangeStart,
                                            @RequestParam(required = false) String rangeEnd,
                                            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {


        return eventAdminService.findAll(users, states, categories, rangeStart, rangeEnd, from, size);
    }
}