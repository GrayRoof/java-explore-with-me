package ru.practikum.ewm.general.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practikum.ewm.general.model.dto.UserDto;
import ru.practikum.ewm.general.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;


@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        return userService.add(userDto);
    }

    @GetMapping()
    public Collection<UserDto> findUsers(@RequestParam Collection<Long> ids,
                                         @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                         @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return userService.getAll(ids, from, size);
    }

}
