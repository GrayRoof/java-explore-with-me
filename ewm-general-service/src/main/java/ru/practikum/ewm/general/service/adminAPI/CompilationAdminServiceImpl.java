package ru.practikum.ewm.general.service.adminAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practikum.ewm.general.exception.NotAvailableException;
import ru.practikum.ewm.general.model.Compilation;
import ru.practikum.ewm.general.model.Event;
import ru.practikum.ewm.general.model.dto.CompilationDto;
import ru.practikum.ewm.general.model.dto.NewCompilationDto;
import ru.practikum.ewm.general.model.dto.UpdateCompilationDto;
import ru.practikum.ewm.general.model.mapper.CompilationMapper;
import ru.practikum.ewm.general.model.mapper.EventMapper;
import ru.practikum.ewm.general.repository.CompilationRepository;
import ru.practikum.ewm.general.service.publicAPI.EventPublicService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompilationAdminServiceImpl implements CompilationAdminService {

    private final CompilationRepository compilationRepository;
    private final EventPublicService eventPublicService;
    @Override
    public CompilationDto create(NewCompilationDto dto) {
        Set<Event> events = new HashSet<>(eventPublicService.findAllByIdIn(dto.getEvents()));
        Compilation newCompilation = CompilationMapper.toCompilation(dto, events);

        return CompilationMapper.toDto(compilationRepository.save(newCompilation),
                events.stream()
                        .map(EventMapper::toShortDto)
                        .collect(Collectors.toList()));
    }

    @Override
    public void pinned(long compilationId) {
        Compilation compilation = compilationRepository.get(compilationId);
        compilation.setPinned(true);
        compilationRepository.save(compilation);
    }

    @Override
    public void addEvent(long compilationId, long eventId) {
        Event event = eventPublicService.getEntity(eventId);
        Compilation compilation = compilationRepository.get(compilationId);
        Set<Event> events = compilation.getEvents();
        if (events.contains(event)) {
            throw new NotAvailableException("Событие уже добавлено в подборку!");
        }
        events.add(event);
        compilation.setEvents(events);
        compilationRepository.save(compilation);
    }

    @Override
    public void delete(long compilationId) {
        Compilation compilation = compilationRepository.get(compilationId);
        compilationRepository.delete(compilation);
    }

    @Override
    public void deletePin(long compilationId) {
        Compilation compilation = compilationRepository.get(compilationId);
        compilation.setPinned(false);
        compilationRepository.save(compilation);

    }

    @Override
    public void deleteEvent(long compilationId, long eventId) {
        Event event = eventPublicService.getEntity(eventId);
        Compilation compilation = compilationRepository.get(compilationId);
        Set<Event> events = compilation.getEvents();
        if (!events.contains(event)) {
            throw new NotAvailableException("Такого события нет в подборке!");
        }
        events.remove(event);
        compilation.setEvents(events);
        compilationRepository.save(compilation);
    }

    @Override
    public CompilationDto update(long compilationId, UpdateCompilationDto dto) {
        Compilation compilation = compilationRepository.get(compilationId);

        Set<Event> events = compilation.getEvents();

        if (events.stream().anyMatch(event -> dto.getEvents().contains(event.getId()))) {
            throw new NotAvailableException("Событие уже добавлено!");
        }
        events.addAll(eventPublicService.findAllByIdIn(dto.getEvents()));
        compilation.setEvents(events);

        return CompilationMapper.toDto(compilationRepository.save(compilation),
                events.stream().map(EventMapper::toShortDto).collect(Collectors.toList()));
    }
}
