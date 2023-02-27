package ru.practikum.ewm.general.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practikum.ewm.general.model.ParticipationRequest;

import java.util.Collection;

public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {

    Collection<ParticipationRequest> findAllByRequesterId(long userId);

    Collection<ParticipationRequest> findAllByEventId(long eventId);

    Boolean existsByEventIdAndRequesterId(long eventId, long userId);

    ParticipationRequest findByEventIdAndRequesterId(long eventId, long userId);

    boolean existsByIdAndRequesterId(long id, long userId);

    @Query(value = "SELECT COUNT(r.id) FROM requests AS r WHERE r.id = ?1 AND r.status LIKE ?2", nativeQuery = true)
    int countAllByEventIdAndStatus(long eventId, String status);

    @Query(value = "SELECT * FROM requests AS r WHERE r.id = ?1 AND r.status NOT LIKE ?2", nativeQuery = true)
    Collection<ParticipationRequest> findAllByEventIdAndNotStatus(long eventId, String status);
}
