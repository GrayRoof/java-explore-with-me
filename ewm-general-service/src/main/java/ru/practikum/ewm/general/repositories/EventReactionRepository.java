package ru.practikum.ewm.general.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practikum.ewm.general.models.Event;
import ru.practikum.ewm.general.models.EventReaction;
import ru.practikum.ewm.general.models.EventReactionKey;
import ru.practikum.ewm.general.models.User;

@Repository
public interface EventReactionRepository extends JpaRepository<EventReaction, EventReactionKey> {

    EventReaction getEventReactionByEventAndUser(Event event, User user);

    @Query(value = "select count(rerLike.eventid) - count(rerDislike.eventid) as rating from events e " +
            "left join rate_event_reactions rerLike on e.id = rerLike.eventid and rerLike.positive is true " +
            "left join rate_event_reactions rerDislike on e.id = rerDislike.eventid and rerDislike.positive is false " +
            "where e.id = ?1 " +
            "group by e.id " +
            "order by rating desc ", nativeQuery = true)
    long getRateForEvent(long eventId);
}
