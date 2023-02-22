package ru.practikum.ewm.general.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practikum.ewm.general.exception.NotFoundException;
import ru.practikum.ewm.general.model.User;

import java.util.Collection;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    default User get(long id) {
        return findById(id).orElseThrow(() -> new NotFoundException("Пользователь с идентификатором #" +
                id + " не зарегистрирован!"));
    }

    Page<User> findAllByIdIn(Collection<Long> ids, Pageable pageable);

    boolean existsByEmail(String email);
}
