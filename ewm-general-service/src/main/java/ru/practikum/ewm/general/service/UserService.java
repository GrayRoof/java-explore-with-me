package ru.practikum.ewm.general.service;

import ru.practikum.ewm.general.exception.NotFoundException;
import ru.practikum.ewm.general.model.dto.UserDto;

import java.util.Collection;

public interface UserService {

    UserDto get(long id) throws NotFoundException;

    Collection<UserDto> getAll(Collection<Long> ids, int from, int size);

    UserDto add(UserDto userDto);

    void delete(long id);
}
