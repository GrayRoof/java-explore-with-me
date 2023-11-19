package ru.practikum.ewm.general.services.privateServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practikum.ewm.general.exceptions.NotAvailableException;
import ru.practikum.ewm.general.exceptions.NotFoundException;
import ru.practikum.ewm.general.models.*;
import ru.practikum.ewm.general.models.dto.*;
import ru.practikum.ewm.general.models.enums.EventState;
import ru.practikum.ewm.general.models.enums.EventStateAction;
import ru.practikum.ewm.general.models.enums.RequestStatus;
import ru.practikum.ewm.general.models.mappers.CategoryMapper;
import ru.practikum.ewm.general.models.mappers.EventMapper;
import ru.practikum.ewm.general.models.mappers.EventReactionMapper;
import ru.practikum.ewm.general.models.mappers.UserMapper;
import ru.practikum.ewm.general.paginations.OffsetPageable;
import ru.practikum.ewm.general.repositories.EventReactionRepository;
import ru.practikum.ewm.general.repositories.EventRepository;
import ru.practikum.ewm.general.services.adminServices.UserAdminService;
import ru.practikum.ewm.general.services.publicServices.CategoryPublicService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventPrivateServiceImpl implements EventPrivateService {

    private final EventRepository eventRepository;
    private final EventReactionRepository reactionRepository;
    private final UserAdminService userAdminService;
    private final CategoryPublicService categoryService;
    private final RequestPrivateService requestPrivateService;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public EventFullDto getForOwner(long eventId, long userId) {
        Event event = eventRepository.get(eventId);
        User user = UserMapper.toUser(userAdminService.get(userId));
        if (!event.getInitiator().equals(user)) {
            throw new NotFoundException("Текущий пользователь не является Инициатором данного события!");
        }
        return EventMapper.toFullDto(event);
    }

    @Override
    public Collection<EventShortDto> getAllForOwner(long userId, Integer from, Integer size) {
        return eventRepository.findAllByInitiatorId(userId, OffsetPageable.of(from, size, Sort.unsorted()))
                .stream()
                .map(EventMapper::toShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<RequestDto> getAllRequestsForEvent(long userId, long eventId) {
        Event event = eventRepository.get(eventId);
        User user = userAdminService.getEntity(userId);
        return requestPrivateService.findAllByEvent(event.getId());
    }

    @Override
    @Transactional
    public EventFullDto createEvent(long userId, NewEventDto dto) {
        User initiator = UserMapper.toUser(userAdminService.get(userId));
        Category category = CategoryMapper.toCategory(categoryService.get(dto.getCategory()));
        EventLocation location = new EventLocation();
        if (dto.getLocation() != null) {
            location.setLat(dto.getLocation().getLat());
            location.setLon(dto.getLocation().getLon());
        }
        Event newEvent = EventMapper.toEvent(dto, initiator, category, location);
        if (newEvent.getCreatedOn().plusHours(2).isAfter(newEvent.getEventDate())) {
            throw new NotAvailableException("Событие нельзя создать менеее чем за 2 часа до его даты проведения!");
        }

        EventFullDto saved = EventMapper.toFullDto(eventRepository.save(newEvent));
                log.info("EVENT: Событие с id = {} создано согласно данным {}", saved.getId(), dto);
        return saved;
    }

    @Override
    public EventFullDto update(long userId, long eventId, EventUpdateDto dto) {

        User requester = userAdminService.getEntity(userId);
        Event event = eventRepository.get(eventId);

        validateInitiator(event.getInitiator(), requester);

        LocalDateTime startEvent = null;
        if (dto.getEventDate() != null) {
            startEvent = LocalDateTime.parse(dto.getEventDate(), FORMATTER);
        }
        validateUpdateData(event.getState(), startEvent, event.getEventDate());

        if (dto.getDescription() != null) {
            event.setDescription(dto.getDescription());
        }
        if (dto.getCategory() != null) {
            event.setCategory(categoryService.getEntity(dto.getCategory()));
        }
        if (dto.getAnnotation() != null) {
            event.setAnnotation(dto.getAnnotation());
        }
        if (startEvent != null) {
            event.setEventDate(startEvent);
        }
        if (dto.getPaid() != null) {
            event.setPaid(dto.getPaid());
        }
        if (dto.getParticipantLimit() != null) {
            event.setParticipantLimit(dto.getParticipantLimit());
        }
        if (dto.getTitle() != null) {
            event.setTitle(dto.getTitle());
        }
        if (dto.getStateAction() != null) {
            event.setState(getStateByAction(event.getState(), dto.getStateAction()));
        }

        EventFullDto fullDto = EventMapper.toFullDto(eventRepository.save(event));
        log.info("EVENT: Событие с id = {} изменено согласно данным {}", eventId, dto);
        return fullDto;
    }

    private EventState getStateByAction(EventState currentState, EventStateAction stateAction) {
        EventState stateToUpdate = currentState;
        if (stateAction.equals(EventStateAction.CANCEL_REVIEW)) {
            if (currentState.equals(EventState.PENDING)) {
                stateToUpdate = EventState.CANCELED;
            }
        } else if (stateAction.equals(EventStateAction.SEND_TO_REVIEW)) {
            if (currentState.equals(EventState.CANCELED)) {
                stateToUpdate = EventState.PENDING;
            }
        }
        return stateToUpdate;
    }

    @Override
    public StatusResponseDto setStatusToRequests(long userId, long eventId, StatusRequestDto statusRequestDto) {
        User requester = userAdminService.getEntity(userId);
        Event event = eventRepository.get(eventId);
        validateInitiator(event.getInitiator(), requester);
        boolean limited = event.getParticipantLimit() > 0;
        long confirmedCount = requestPrivateService.getCountConfirmedForEvent(eventId);
        long eventCapacity = limited ? event.getParticipantLimit() - confirmedCount : Long.MAX_VALUE;
        RequestStatus status = statusRequestDto.getStatus();
        StatusResponseDto result = new StatusResponseDto();
        if (status != null) {
            if (status.equals(RequestStatus.REJECTED)) {
                result = rejectRequests(statusRequestDto.getRequestIds());
            } else if (status.equals(RequestStatus.CONFIRMED)) {
                result = confirmByLimitRequests(statusRequestDto.getRequestIds(), eventCapacity);
            }
        }

        return result;
    }

    @Override
    public EventReactionDto setReaction(long userId, long eventId, Boolean isPositive) {
        log.info("REACTION: try to set reaction to {} by {} action is {}", eventId, userId, isPositive);
        User user = userAdminService.getEntity(userId);
        Event event = eventRepository.get(eventId);
        EventReaction result;
        validateParticipant(event, user);
        if (isPositive != null) {
            EventReaction existReaction = reactionRepository.getEventReactionByEventAndUser(event, user);
            if (isPositive) {
                if (existReaction != null) {
                    if (existReaction.isPositive()) {
                        throw new NotAvailableException("Нельзя поставить лайк больше одного раза!");
                    }
                    existReaction.setPositive(true);
                    result = reactionRepository.save(existReaction);
                } else {
                    result = reactionRepository.save(new EventReaction(user, event, true));
                }
            } else {
                if (existReaction != null) {
                    if (!existReaction.isPositive()) {
                        throw new NotAvailableException("Нельзя поставить дизлайк больше одного раза!");
                    }
                    existReaction.setPositive(false);
                    result = reactionRepository.save(existReaction);
                } else {
                    result = reactionRepository.save(new EventReaction(user, event, false));
                }
            }
        } else {
            throw new NotAvailableException("Реакция не задана!");
        }
        return EventReactionMapper.toDto(result);
    }

    @Override
    public void deleteReaction(long userId, long eventId) {
        log.info("REACTION: try to delete reaction to {} by {}", eventId, userId);
        User user = userAdminService.getEntity(userId);
        Event event = eventRepository.get(eventId);
        validateParticipant(event, user);
        EventReaction existReaction = reactionRepository.getEventReactionByEventAndUser(event, user);
        if (existReaction != null) {
            reactionRepository.delete(existReaction);
        } else {
            throw new NotAvailableException("Не найдено реакции для этого События!");
        }
    }

    private void validateParticipant(Event event, User user) {
        if (requestPrivateService.findAllByRequesterId(user.getId())
                .stream().noneMatch(requestDto -> requestDto.getRequester() == user.getId()
                        && requestDto.getEvent() == event.getId())) {
            throw new NotAvailableException("Только участники мероприятия могут оставить реакцию!");
        }
        if (event.getInitiator().equals(user)) {
            throw new NotAvailableException("Организатор события не может оставить реакцию на свое событие!");
        }
    }

    private StatusResponseDto rejectRequests(List<Long> requestIds) {
        Collection<RequestDto> rejected = requestPrivateService.rejectRequests(requestIds);
        StatusResponseDto responseDto = new StatusResponseDto();
        responseDto.setRejectedRequests(rejected);
        responseDto.setConfirmedRequests(List.of());
        return responseDto;
    }

    private StatusResponseDto confirmByLimitRequests(List<Long> requestIds, long eventCapacity) {
        if (eventCapacity > 0) {
            Collection<RequestDto> confirmed = requestPrivateService
                    .confirmRequests(requestIds.stream().limit(eventCapacity).collect(Collectors.toList()));
            Collection<RequestDto> rejected = requestPrivateService
                    .rejectRequests(requestIds.stream().skip(eventCapacity).collect(Collectors.toList()));
            StatusResponseDto responseDto = new StatusResponseDto();
            responseDto.setRejectedRequests(rejected);
            responseDto.setConfirmedRequests(confirmed);
            return responseDto;
        } else {
            throw new NotAvailableException("Превышено количество заявок на участие в Событии!");
        }
    }

    private void validateInitiator(User initiator, User requester) {
        if (!initiator.equals(requester)) {
            throw new NotFoundException("Пользователь не является инициатором События!");
        }
    }

    private void validateUpdateData(EventState state, LocalDateTime eventDateToUpdate, LocalDateTime eventDate) {

        if (state.equals(EventState.PUBLISHED)) {
            throw new NotAvailableException("Невозможно изменить опубликованное событие!");
        }
        if (eventDateToUpdate != null) {
            if (LocalDateTime.now().plusHours(2).isAfter(eventDateToUpdate)) {
                throw new NotAvailableException("Событие должно начаться не раньше, " +
                        "чем через 2 часа от текущего времени");
            }
        } else {
            if (LocalDateTime.now().plusHours(2).isAfter(eventDate)) {
                throw new NotAvailableException("Невозможно изменить дату события, " +
                        "которое произойдет в ближайшие 2 часа!");
            }
        }
    }
}
