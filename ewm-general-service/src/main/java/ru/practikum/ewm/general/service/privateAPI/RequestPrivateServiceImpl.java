package ru.practikum.ewm.general.service.privateAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practikum.ewm.general.exception.NotAvailableException;
import ru.practikum.ewm.general.model.*;
import ru.practikum.ewm.general.model.dto.RequestDto;
import ru.practikum.ewm.general.model.enums.EventState;
import ru.practikum.ewm.general.model.enums.RequestStatus;
import ru.practikum.ewm.general.model.mapper.RequestMapper;
import ru.practikum.ewm.general.repository.RequestRepository;
import ru.practikum.ewm.general.service.adminAPI.UserAdminService;
import ru.practikum.ewm.general.service.publicAPI.EventPublicService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RequestPrivateServiceImpl implements RequestPrivateService {

    private final RequestRepository requestRepository;
    private final UserAdminService userAdminService;
    private final EventPublicService eventPublicService;

    @Override
    public Collection<RequestDto> findAllByRequesterId(long userId) {
        return requestRepository.findAllByRequesterId(userAdminService.getEntity(userId).getId())
                .stream()
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<RequestDto> findAllByEvent(long id) {
        return requestRepository.findAllByEventId(id).stream()
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RequestDto create(long userId, long eventId) {
        User requester = userAdminService.getEntity(userId);
        Event event = eventPublicService.getEntity(eventId);
        checkNewRequest(event, requester);

        RequestStatus status = RequestStatus.PENDING;
        if (!event.isRequestModeration()) {
            status = RequestStatus.CONFIRMED;
        }
        ParticipationRequest request = new ParticipationRequest();
        request.setRequester(requester);
        request.setCreated(LocalDateTime.now());
        request.setEvent(event);
        request.setStatus(status);
        return RequestMapper.toDto(requestRepository.save(request));
    }

    @Override
    public RequestDto cancel(long userId, long requestId) {
        User requester = userAdminService.getEntity(userId);
        ParticipationRequest request = requestRepository.get(requestId);
        if (!request.getRequester().equals(requester)) {
            throw new NotAvailableException("not requester");
        }

        if (request.getStatus().equals(RequestStatus.CONFIRMED)) {
            throw new NotAvailableException("already CONFIRMED");
        }
        request.setStatus(RequestStatus.CANCELED);
        return RequestMapper.toDto(requestRepository.save(request));
    }

    @Override
    public int getCountConfirmedForEvent(long eventId) {
        return requestRepository.findAllByEventIdAndStatus(eventId, RequestStatus.CONFIRMED).size();
    }

    @Override
    public Collection<RequestDto> rejectRequests(List<Long> ids) {
        Collection<ParticipationRequest> requests = requestRepository.findAllByIdIn(ids);
        if (requests.stream().anyMatch(request -> request.getStatus().equals(RequestStatus.CONFIRMED))) {
            throw new NotAvailableException("already CONFIRMED");
        }
        return requests.stream().peek(request -> request.setStatus(RequestStatus.REJECTED))
                .map(requestRepository::save)
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<RequestDto> confirmRequests(List<Long> ids) {
        return requestRepository.findAllByIdIn(ids)
                .stream().peek(request -> request.setStatus(RequestStatus.CONFIRMED))
                .map(requestRepository::save)
                .map(RequestMapper::toDto)
                .collect(Collectors.toList());
    }


    private void checkNewRequest(Event event, User requester) {
        if (event.getInitiator().equals(requester)) {
            throw new NotAvailableException("initiator");
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new NotAvailableException("not published");
        }

        Collection<ParticipationRequest> requests = requestRepository.findAllByEventIdAndRequesterIdAndStatusIn(
                event.getId(), requester.getId(), List.of(RequestStatus.PENDING, RequestStatus.CONFIRMED));
        if (!requests.isEmpty()) {
            throw new NotAvailableException("already exist");
        }
        long eventCapacity = event.getParticipantLimit() - getCountConfirmedForEvent(event.getId());
        if (event.getParticipantLimit() > 0 &&  eventCapacity < 1) {
            throw new NotAvailableException("not available");
        }
    }
}
