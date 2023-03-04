package ru.practikum.ewm.general.services.adminServices;

import ru.practikum.ewm.general.exceptions.NotFoundException;
import ru.practikum.ewm.general.models.User;
import ru.practikum.ewm.general.models.dto.UserDto;

import java.util.Collection;

public interface UserAdminService {

    UserDto get(long id) throws NotFoundException;

    Collection<UserDto> getAll(Collection<Long> ids, int from, int size);

    User getEntity(long id);

    UserDto add(UserDto userDto);

    void delete(long id);
}
