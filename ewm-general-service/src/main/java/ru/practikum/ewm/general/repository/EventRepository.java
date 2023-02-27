package ru.practikum.ewm.general.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practikum.ewm.general.exception.NotFoundException;
import ru.practikum.ewm.general.model.Event;

import java.util.Collection;


@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    default Event get(long eventId) {
         return findById(eventId).orElseThrow(() ->
                new NotFoundException("Событие с id = " + " не найдено."));
    }

    Collection<Event> findAllByCategoryId(long categoryId);

    Collection<Event> findAllByInitiatorId(long userId, Pageable pageable);

}
