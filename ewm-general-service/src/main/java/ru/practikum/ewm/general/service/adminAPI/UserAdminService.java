package ru.practikum.ewm.general.service.adminAPI;

import ru.practikum.ewm.general.exception.NotFoundException;
import ru.practikum.ewm.general.model.User;
import ru.practikum.ewm.general.model.dto.UserDto;

import java.util.Collection;

public interface UserAdminService {

    UserDto get(long id) throws NotFoundException;

    Collection<UserDto> getAll(Collection<Long> ids, int from, int size);

    User getEntity(long id);

    UserDto add(UserDto userDto);

    void delete(long id);
}