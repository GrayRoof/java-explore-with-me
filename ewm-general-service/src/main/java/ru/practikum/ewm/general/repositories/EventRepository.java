package ru.practikum.ewm.general.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practikum.ewm.general.exceptions.NotFoundException;
import ru.practikum.ewm.general.models.Event;

import java.util.Collection;


@Repository
public interface EventRepository extends JpaRepository<Event, Long>, EventExtraFilterRepository {

    default Event get(long eventId) {
         return findById(eventId).orElseThrow(() ->
                new NotFoundException("Событие с id = " + " не найдено."));
    }

    Collection<Event> findAllByCategoryId(long categoryId);

    Page<Event> findAllByInitiatorId(long userId, Pageable pageable);

    Collection<Event> findAllByIdIn(Collection<Long> ids);
}
