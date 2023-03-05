package ru.practikum.ewm.general.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practikum.ewm.general.models.Event;
import ru.practikum.ewm.general.models.EventReaction;
import ru.practikum.ewm.general.models.EventReactionKey;
import ru.practikum.ewm.general.models.User;

@Repository
public interface EventReactionRepository extends JpaRepository<EventReaction, EventReactionKey> {

    EventReaction getEventReactionByEventAndUser(Event event, User user);
}
