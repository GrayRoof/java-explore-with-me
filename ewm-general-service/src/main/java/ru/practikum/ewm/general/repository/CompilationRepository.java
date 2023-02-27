package ru.practikum.ewm.general.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practikum.ewm.general.exception.NotFoundException;
import ru.practikum.ewm.general.model.Compilation;

import java.util.Collection;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    default Compilation get(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException("Компиляция с идентификатором #" +
                               id + " не зарегистрирована!"));
    }

    Collection<Compilation> findByPinned(Boolean pinned, Pageable pageable);
}
