package ru.practikum.ewm.general.service.adminAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practikum.ewm.general.exception.NotFoundException;
import ru.practikum.ewm.general.model.User;
import ru.practikum.ewm.general.model.UserMapper;
import ru.practikum.ewm.general.model.dto.UserDto;
import ru.practikum.ewm.general.pagination.OffsetPageable;
import ru.practikum.ewm.general.repository.UserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAdminServiceImpl implements UserAdminService {

    private final UserRepository userRepository;


    @Override
    public UserDto get(long id) throws NotFoundException {
        return UserMapper.toDto(userRepository.get(id));
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
    public UserDto add(UserDto userDto) {
        User user = userRepository.save(UserMapper.toUser(userDto));
        return UserMapper.toDto(user);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
