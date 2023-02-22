package ru.practikum.ewm.general.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practikum.ewm.general.model.dto.UserDto;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public static User toUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName() == null ? "" : userDto.getName());
        user.setEmail(userDto.getEmail());
        return user;
    }
}
