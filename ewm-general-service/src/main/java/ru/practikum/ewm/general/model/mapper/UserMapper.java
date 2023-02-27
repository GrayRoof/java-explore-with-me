package ru.practikum.ewm.general.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practikum.ewm.general.model.User;
import ru.practikum.ewm.general.model.dto.UserDto;
import ru.practikum.ewm.general.model.dto.UserShortDto;

@Component
@RequiredArgsConstructor
public class UserMapper {

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
