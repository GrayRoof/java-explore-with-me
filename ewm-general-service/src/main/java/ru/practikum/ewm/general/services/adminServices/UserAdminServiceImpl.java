package ru.practikum.ewm.general.services.adminServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practikum.ewm.general.exceptions.NotFoundException;
import ru.practikum.ewm.general.models.User;
import ru.practikum.ewm.general.models.mappers.UserMapper;
import ru.practikum.ewm.general.models.dto.UserDto;
import ru.practikum.ewm.general.paginations.OffsetPageable;
import ru.practikum.ewm.general.repositories.UserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAdminServiceImpl implements UserAdminService {

    private final UserRepository userRepository;


    @Override
    public UserDto get(long id) throws NotFoundException {
        return UserMapper.toDto(getEntity(id));
    }

    @Override
    public Collection<UserDto> getAll(Collection<Long> ids, int from, int size) {
        Page<User> result;
        if (ids == null || ids.isEmpty()) {
            result = userRepository.findAll(OffsetPageable.of(from, size, Sort.unsorted()));
        } else {
            result = userRepository.findAllByIdIn(ids, OffsetPageable.of(from, size, Sort.unsorted()));
        }
        return result.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public User getEntity(long id) {
        return userRepository.get(id);
    }

    @Override
    public UserDto add(UserDto userDto) {
        User user = userRepository.save(UserMapper.toUser(userDto));
        return UserMapper.toDto(user);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
