package ru.practikum.ewm.general.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practikum.ewm.general.exception.NotValidException;
import ru.practikum.ewm.general.model.SortMethod;
import ru.practikum.ewm.general.model.dto.EventFullDto;
import ru.practikum.ewm.general.service.publicAPI.EventPublicService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventPublicController {

    private final EventPublicService eventPublicService;

    @GetMapping
    public Collection<EventFullDto> findAll(@RequestParam(required = false) String text,
                                            @RequestParam(required = false) List<Long> categories,
                                            @RequestParam(required = false) Boolean paid,
                                            @RequestParam(required = false) String rangeStart,
                                            @RequestParam(required = false) String rangeEnd,
                                            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                            @RequestParam(required = false) SortMethod sortMethod,
                                            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size,
                                            HttpServletRequest request) {

        if (sortMethod != null && sortMethod.equals(SortMethod.UNSUPPORTED_METHOD)) {
            throw new NotValidException("UNSUPPORTED_METHOD");
        }

        return eventPublicService.getAll(text, categories, paid,
                rangeStart, rangeEnd, onlyAvailable, sortMethod, from, size, request);
    }

    @GetMapping("{eventId}")
    public EventFullDto findById(@Positive @PathVariable long eventId, HttpServletRequest request) {

        return eventPublicService.get(eventId, request);
    }
}
