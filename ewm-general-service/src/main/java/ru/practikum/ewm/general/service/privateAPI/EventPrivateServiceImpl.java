package ru.practikum.ewm.general.service.privateAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practikum.ewm.general.exception.NotFoundException;
import ru.practikum.ewm.general.exception.NotValidException;
import ru.practikum.ewm.general.model.*;
import ru.practikum.ewm.general.model.dto.*;
import ru.practikum.ewm.general.model.mapper.CategoryMapper;
import ru.practikum.ewm.general.model.mapper.EventMapper;
import ru.practikum.ewm.general.model.mapper.UserMapper;
import ru.practikum.ewm.general.pagination.OffsetPageable;
import ru.practikum.ewm.general.repository.EventRepository;
import ru.practikum.ewm.general.service.adminAPI.UserAdminService;
import ru.practikum.ewm.general.service.publicAPI.CategoryPublicService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;

/* TODO set extra data to event: confirmedRequests and views */

@Service
@Slf4j
@RequiredArgsConstructor
public class EventPrivateServiceImpl implements EventPrivateService {

    private final EventRepository eventRepository;
    private final UserAdminService userAdminService;
    private final CategoryPublicService categoryService;

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
        return null;
    }

    @Override
    @Transactional
    public EventFullDto createEvent(long userId, NewEventDto dto) {
        User initiator = UserMapper.toUser(userAdminService.get(userId));
        Category category = CategoryMapper.toCategory(categoryService.get(dto.getCategory()));
        Event newEvent = EventMapper.toEvent(dto, initiator, category);
        if (newEvent.getCreatedOn().plusHours(2).isAfter(newEvent.getEventDate())) {
            throw new NotValidException("Событие нельзя создать менеее чем за 2 часа до его даты проведения!");
        }

        return EventMapper.toFullDto(eventRepository.save(newEvent));
    }

    @Override
    public EventFullDto update(long userId, EventUpdateDto dto) {
        User initiator = UserMapper.toUser(userAdminService.get(userId));

        Event event = eventRepository.get(dto.getEventId());
        if (!event.getInitiator().equals(initiator)) {
            throw new NotFoundException("Not owner");
        }
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new NotValidException("PUBLISHED");
        }
        if (dto.getAnnotation() != null) {
            event.setAnnotation(dto.getAnnotation());
        }
        if (dto.getCategory() != null) {
            event.setCategory(CategoryMapper.toCategory(categoryService.get(dto.getCategory())));
        }
        if (dto.getDescription() != null) {
            event.setDescription(dto.getDescription());
        }
        if (dto.getEventDate() != null) {
            LocalDateTime startEvent = LocalDateTime.parse(dto.getEventDate(), DateTimeFormatter
                    .ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (LocalDateTime.now().plusHours(2).isAfter(startEvent)) {
                throw new NotValidException("createTimeLag");
            }
            event.setEventDate(startEvent);
        } else {
            if (LocalDateTime.now().plusHours(2).isAfter(event.getEventDate())) {
                throw new NotValidException("createTimeLag");
            }
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
        event.setState(EventState.PENDING);
        EventFullDto fullDto = EventMapper.toFullDto(eventRepository.save(event));
        log.info("Событие с id = {} изменено согласно данным {}", dto.getEventId(), dto);
        return fullDto;
    }

    @Override
    public EventFullDto cancelEvent(long eventId, long userId) {
        User initiator = UserMapper.toUser(userAdminService.get(userId));
        Event event = eventRepository.get(eventId);
        if (!event.getInitiator().equals(initiator)) {
            throw new NotFoundException("initiator");
        }
        if (event.getState().equals(EventState.PENDING)) {
            event.setState(EventState.CANCELED);
            eventRepository.save(event);
            log.info("Событие с id = {} было отменено", eventId);
            return EventMapper.toFullDto(event);
        } else {
            throw new NotValidException("not PENDING");
        }
    }

    @Override
    public RequestDto confirmRequest(long userId, long eventId, long reqId) {
        return null;
    }

    @Override
    public RequestDto rejectRequest(long userId, long eventId, long reqId) {
        return null;
    }
}
