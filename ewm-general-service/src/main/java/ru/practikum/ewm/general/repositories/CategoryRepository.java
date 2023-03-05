package ru.practikum.ewm.general.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practikum.ewm.general.exceptions.NotFoundException;
import ru.practikum.ewm.general.models.Category;


public interface CategoryRepository extends JpaRepository<Category, Long> {

    default Category get(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException("Категория с идентификатором #" +
                id + " не зарегистрирована!"));
    }

    Page<Category> findAll(Pageable pageable);

}
