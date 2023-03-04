package ru.practikum.ewm.general.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practikum.ewm.general.models.dto.RequestDto;
import ru.practikum.ewm.general.services.privateAPI.RequestPrivateService;

import javax.validation.constraints.Positive;
import java.util.Collection;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class RequestPrivateController {

    private final RequestPrivateService requestPrivateService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto create(@Positive @PathVariable long userId,
                                          @Positive @RequestParam(name = "eventId") long eventId) {
        return requestPrivateService.create(userId, eventId);
    }

    @PatchMapping("{requestId}/cancel")
    public RequestDto cancel(@Positive @PathVariable long userId, @Positive @PathVariable long requestId) {
        return requestPrivateService.cancel(userId, requestId);
    }

    @GetMapping()
    public Collection<RequestDto> findAllByRequesterId(@Positive @PathVariable long userId) {
        return requestPrivateService.findAllByRequesterId(userId);
    }
}