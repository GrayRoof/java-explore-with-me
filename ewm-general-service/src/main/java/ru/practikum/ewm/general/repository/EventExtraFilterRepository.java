package ru.practikum.ewm.general.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practikum.ewm.general.model.Event;
import ru.practikum.ewm.general.model.EventSearchFilter;
import ru.practikum.ewm.general.model.EventState;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventExtraFilterRepository {

    private  final EntityManager entityManager;

    public Collection<Event> findAll(EventSearchFilter filter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = criteriaBuilder.createQuery(Event.class);
        Root<Event> event = query.from(Event.class);
        List<Predicate> predicates = new ArrayList<>();
        if (filter.getText() != null) {
            predicates
                    .add(criteriaBuilder.or(
                            criteriaBuilder
                                    .like(criteriaBuilder
                                            .upper(event.get("annotation")), "%" + filter.getText().toUpperCase() + "%"),
                            criteriaBuilder
                                    .like(criteriaBuilder
                                            .upper(event.get("description")), "%" + filter.getText().toUpperCase() + "%")
                    ));
        }
        List<Long> categories = filter.getCategories();
        if (categories != null && !categories.isEmpty()) {
            predicates.add(criteriaBuilder.in(event.get("category").get("id")).value(categories));
        }
        if (filter.getPaid() != null) {
            predicates.add(criteriaBuilder.equal(event.get("paid"), filter.getPaid()));
        }
        if (filter.getRangeStart() != null) {
            predicates.add(criteriaBuilder.greaterThan(event.get("eventDate"), filter.getRangeStart()));
        }
        if (filter.getRangeEnd() != null) {
            predicates.add(criteriaBuilder.lessThan(event.get("eventDate"), filter.getRangeEnd()));
        }
        List<Long> users = filter.getUsers();
        if (users != null && !users.isEmpty()) {
            predicates.add(criteriaBuilder.in(event.get("initiator").get("id")).value(users));
        }
        List<EventState> states = filter.getStates();
        if (states != null && !states.isEmpty()) {
            predicates.add(criteriaBuilder.in(event.get("state")).value(states));
        }
        if (filter.isOnlyAvailable()) {
            predicates.add(criteriaBuilder.or(
                    criteriaBuilder.greaterThan(event.get("participantLimit"), event.get("confirmedRequests")),
                    criteriaBuilder.equal(event.get("participantLimit"), 0)));
        }
        Order order;
        switch (filter.getSortMethod()) {
            case EVENT_DATE:
                order = criteriaBuilder.asc(event.get("eventDate"));
                break;
            case VIEWS:
                order = criteriaBuilder.desc(event.get("views"));
                break;
            default:
                order = criteriaBuilder.asc(event.get("id"));
                break;
        }

        return entityManager.createQuery(query.select(event).where(criteriaBuilder.and(predicates.toArray(Predicate[]::new)))
                        .orderBy(order))
                .setFirstResult(filter.getFrom())
                .setMaxResults(filter.getSize())
                .getResultList();
    }
}
