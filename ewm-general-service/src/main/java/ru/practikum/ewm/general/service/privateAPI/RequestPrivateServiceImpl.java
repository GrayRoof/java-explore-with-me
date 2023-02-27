package ru.practikum.ewm.general.service.privateAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practikum.ewm.general.exception.ForbiddenException;
import ru.practikum.ewm.general.model.*;
import ru.practikum.ewm.general.model.dto.RequestDto;
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
        return RequestMapper.toDto(request);
    }

    @Override
    public RequestDto cancel(long userId, long requestId) {
        User requester = userAdminService.getEntity(userId);
        ParticipationRequest request = requestRepository.get(requestId);
        if (!request.getRequester().equals(requester)) {
            throw new ForbiddenException("not requester");
        }
        request.setStatus(RequestStatus.CANCELED);
        return RequestMapper.toDto(requestRepository.save(request));
    }

    private void checkNewRequest(Event event, User requester) {
        if (event.getInitiator().equals(requester)) {
            throw new ForbiddenException("initiator");
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ForbiddenException("not published");
        }
        if (requestRepository.existsByIdAndRequesterIdAndStatusIn(
                event.getId(), requester.getId(), List.of(RequestStatus.CONFIRMED, RequestStatus.PENDING))) {
            throw new ForbiddenException("already exist");
        }
        if (!eventPublicService.isEventAvailable(event.getId())) {
            throw new ForbiddenException("not available");
        }
    }
}
