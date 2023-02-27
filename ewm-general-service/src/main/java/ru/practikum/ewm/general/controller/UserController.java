package ru.practikum.ewm.general.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practikum.ewm.general.model.dto.UserDto;
import ru.practikum.ewm.general.service.adminAPI.UserAdminService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;


@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
public class UserController {

    private final UserAdminService userAdminService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        return userAdminService.add(userDto);
    }

    @GetMapping()
    public Collection<UserDto> findUsers(@RequestParam Collection<Long> ids,
                                         @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                         @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return userAdminService.getAll(ids, from, size);
    }

}
