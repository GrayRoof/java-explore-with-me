package ru.practikum.ewm.general.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practikum.ewm.general.model.Event;
import ru.practikum.ewm.general.model.EventState;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventExtraFilterRepository {

    private  final EntityManager entityManager;

    public Collection<Event> findAll(String text,
                                     List<Long> categories,
                                     Boolean paid,
                                     LocalDateTime rangeStart,
                                     LocalDateTime rangeEnd,
                                     Integer from,
                                     Integer size,
                                     List<Long> users,
                                     List<EventState> states) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = criteriaBuilder.createQuery(Event.class);
        Root<Event> event = query.from(Event.class);
        List<Predicate> predicates = new ArrayList<>();
        if (text != null) {
            predicates
                    .add(criteriaBuilder.or(
                            criteriaBuilder
                                    .like(criteriaBuilder
                                            .upper(event.get("annotation")), "%" + text.toUpperCase() + "%"),
                            criteriaBuilder
                                    .like(criteriaBuilder
                                            .upper(event.get("description")), "%" + text.toUpperCase() + "%")
                    ));
        }
        if (categories != null && !categories.isEmpty()) {
            predicates.add(criteriaBuilder.in(event.get("category").get("id")).value(categories));
        }
        if (paid != null) {
            predicates.add(criteriaBuilder.equal(event.get("paid"), paid));
        }
        if (rangeStart != null) {
            predicates.add(criteriaBuilder.greaterThan(event.get("eventDate"), rangeStart));
        }
        if (rangeEnd != null) {
            predicates.add(criteriaBuilder.lessThan(event.get("eventDate"), rangeEnd));
        }
        if (users != null && !users.isEmpty()) {
            predicates.add(criteriaBuilder.in(event.get("initiator").get("id")).value(users));
        }
        if (states != null && !states.isEmpty()) {
            predicates.add(criteriaBuilder.in(event.get("state")).value(states));
        }

        return entityManager.createQuery(query.select(event).where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))))
                .setFirstResult(from)
                .setMaxResults(size)
                .getResultList();
    }
}
