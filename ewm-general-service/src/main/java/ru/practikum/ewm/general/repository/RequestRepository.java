package ru.practikum.ewm.general.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practikum.ewm.general.exception.NotFoundException;
import ru.practikum.ewm.general.model.ParticipationRequest;
import ru.practikum.ewm.general.model.RequestStatus;

import java.util.Collection;


public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {

    default ParticipationRequest get(long requestId) {
        return findById(requestId).orElseThrow(() -> new NotFoundException("not exist"));
    }
    Collection<ParticipationRequest> findAllByRequesterId(long userId);

    Collection<ParticipationRequest> findAllByEventIdAndStatus(long eventId, RequestStatus status);

    boolean existsByIdAndRequesterIdAndStatusIn(long id, long userId, Collection<RequestStatus> statuses);
}
