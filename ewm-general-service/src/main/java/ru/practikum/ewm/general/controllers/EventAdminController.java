package ru.practikum.ewm.general.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practikum.ewm.general.models.enums.EventState;
import ru.practikum.ewm.general.models.dto.EventAdminUpdateDto;
import ru.practikum.ewm.general.models.dto.EventFullDto;
import ru.practikum.ewm.general.services.adminServices.EventAdminService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
public class EventAdminController {

    private final EventAdminService eventAdminService;

    @GetMapping
    public Collection<EventFullDto> findAll(@RequestParam(required = false) List<Long> users,
                                            @RequestParam(required = false) List<EventState> states,
                                            @RequestParam(required = false) List<Long> categories,
                                            @RequestParam(required = false) String rangeStart,
                                            @RequestParam(required = false) String rangeEnd,
                                            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {


        return eventAdminService.getAll(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto update(@Positive @PathVariable Long eventId,
                                    @RequestBody EventAdminUpdateDto eventAdminUpdateDto) {
        return eventAdminService.update(eventId, eventAdminUpdateDto);
    }
}