package ru.practikum.ewm.general.models.mappers;

import ru.practikum.ewm.general.models.User;
import ru.practikum.ewm.general.models.dto.UserDto;
import ru.practikum.ewm.general.models.dto.UserShortDto;

public class UserMapper {

    private UserMapper() {
    }

    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public static UserShortDto toShortDto(User user) {
        UserShortDto userShortDto = new UserShortDto();
        userShortDto.setId(user.getId());
        userShortDto.setName(user.getName());
        return userShortDto;
    }

    public static User toUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName() == null ? "" : userDto.getName());
        user.setEmail(userDto.getEmail());
        return user;
    }
}
