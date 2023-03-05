package ru.practikum.ewm.general.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practikum.ewm.general.exceptions.NotFoundException;
import ru.practikum.ewm.general.models.ParticipationRequest;
import ru.practikum.ewm.general.models.enums.RequestStatus;

import java.util.Collection;
import java.util.List;


public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {

    default ParticipationRequest get(long requestId) {
        return findById(requestId).orElseThrow(() -> new NotFoundException("not exist"));
    }

    Collection<ParticipationRequest> findAllByRequesterId(long userId);

    Collection<ParticipationRequest> findAllByEventIdAndStatus(long eventId, RequestStatus status);

    Collection<ParticipationRequest> findAllByEventIdAndRequesterIdAndStatusIn(Long id, long requesterId,
                                                                          Collection<RequestStatus> status);

    Collection<ParticipationRequest> findAllByIdIn(List<Long> ids);

    Collection<ParticipationRequest> findAllByEventId(long id);
}
