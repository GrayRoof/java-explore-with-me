package ru.practikum.ewm.general.service.adminAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practikum.ewm.general.exception.NotAvailableException;
import ru.practikum.ewm.general.model.*;
import ru.practikum.ewm.general.model.dto.EventAdminUpdateDto;
import ru.practikum.ewm.general.model.dto.EventFullDto;
import ru.practikum.ewm.general.model.mapper.EventMapper;
import ru.practikum.ewm.general.repository.EventExtraFilterRepository;
import ru.practikum.ewm.general.repository.EventRepository;
import ru.practikum.ewm.general.service.publicAPI.CategoryPublicService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// TODO
@Service
@Slf4j
@RequiredArgsConstructor
public class EventAdminServiceImpl implements EventAdminService {

    private final EventRepository eventRepository;
    private final EventExtraFilterRepository filterRepository;
    private final CategoryPublicService categoryPublicService;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Transactional
    @Override
    public EventFullDto update(long eventId, EventAdminUpdateDto dto) {
        Event event = eventRepository.get(eventId);

        EventState state = getStateForEvent(event, dto.getStateAction());
        event.setState(state);

        if (dto.getAnnotation() != null) {
            event.setAnnotation(dto.getAnnotation());
        }
        if (dto.getCategory() != null) {
            event.setCategory(categoryPublicService.getEntity(dto.getCategory()));
        }
        if (dto.getDescription() != null) {
            event.setDescription(dto.getDescription());
        }
        if (dto.getEventDate() != null) {
            LocalDateTime eventDate = LocalDateTime.parse(dto.getEventDate(), FORMATTER);
            LocalDateTime publishedOn = event.getPublishedOn();

            if (publishedOn != null && eventDate.isBefore(publishedOn.plusHours(2))
                    || eventDate.isBefore(LocalDateTime.now())) {
                throw new NotAvailableException("eventDate. Value: " + eventDate);
            }
            event.setEventDate(eventDate);
        }
        if (dto.getPaid() != null) {
            event.setPaid(dto.getPaid());
        }
        if (dto.getParticipantLimit() != null) {
            event.setParticipantLimit(dto.getParticipantLimit());
        }
        if (dto.getRequestModeration() != null) {
            event.setRequestModeration(dto.getRequestModeration());
        }
        if (dto.getTitle() != null) {
            event.setTitle(dto.getTitle());
        }

        return EventMapper.toFullDto(event);
    }

    @Override
    public List<EventFullDto> getAll(List<Long> users,
                                     List<EventState> states,
                                     List<Long> categories,
                                     String rangeStart,
                                     String rangeEnd,
                                     Integer from,
                                     Integer size) {

        EventSearchFilter filter = EventSearchFilter.builder()
                .users(users)
                .states(states)
                .categories(categories)
                .rangeStart(null)
                .rangeEnd(null)
                .sortMethod(SortMethod.UNSUPPORTED_METHOD)
                .from(from)
                .size(size)
                .build();

        return filterRepository.findAll(filter).stream()
                .map(EventMapper::toFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Event> getAllByCategory(long id) {
        return eventRepository.findAllByCategoryId(id);
    }

    private EventState getStateForEvent(Event event, EventStateAction action) {
        EventState result = EventState.CANCELED;
        if (action != null) {
            switch (action) {
                case PUBLISH_EVENT:
                    if (!event.getState().equals(EventState.PENDING)) {
                        throw new NotAvailableException("not PENDING");
                    }
                    if (!event.getEventDate().minusHours(2).isAfter(LocalDateTime.now())) {
                            throw new NotAvailableException("time out");
                    }
                    result = EventState.PUBLISHED;
                    break;
                case REJECT_EVENT:
                    if (event.getState().equals(EventState.PUBLISHED)) {
                        throw new NotAvailableException("is PUBLISHED");
                    }
                    break;
            }
        }
        return result;
    }
}
