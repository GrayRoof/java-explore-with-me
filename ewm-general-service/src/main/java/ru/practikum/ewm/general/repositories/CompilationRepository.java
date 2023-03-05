package ru.practikum.ewm.general.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practikum.ewm.general.exceptions.NotFoundException;
import ru.practikum.ewm.general.models.Compilation;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    default Compilation get(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException("Компиляция с идентификатором #" +
                               id + " не зарегистрирована!"));
    }

    Page<Compilation> findByPinned(Boolean pinned, Pageable pageable);
}
