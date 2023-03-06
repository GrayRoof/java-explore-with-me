package ru.practikum.ewm.general.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query(value = "select e.*, count(rerLike.eventid) - count(rerDislike.eventid) as rating from events e " +
            "left join rate_event_reactions rerLike on e.id = rerLike.eventid and rerLike.positive is true " +
            "left join rate_event_reactions rerDislike on e.id = rerDislike.eventid and rerDislike.positive is false " +
            "where e.categoryid = ?1 " +
            "group by e.id " +
            "order by rating desc ", nativeQuery = true)
    Collection<Event> getCategoryRating(long categoryId);


}
