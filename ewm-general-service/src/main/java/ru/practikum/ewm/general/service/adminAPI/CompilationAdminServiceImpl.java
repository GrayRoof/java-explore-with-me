package ru.practikum.ewm.general.service.adminAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practikum.ewm.general.exception.ForbiddenException;
import ru.practikum.ewm.general.model.Compilation;
import ru.practikum.ewm.general.model.Event;
import ru.practikum.ewm.general.model.dto.CompilationDto;
import ru.practikum.ewm.general.model.dto.NewCompilationDto;
import ru.practikum.ewm.general.model.mapper.CompilationMapper;
import ru.practikum.ewm.general.model.mapper.EventMapper;
import ru.practikum.ewm.general.repository.CompilationRepository;
import ru.practikum.ewm.general.service.publicAPI.EventPublicService;

import java.util.Set;
import java.util.stream.Collectors;

// TODO almost
@Service
@Slf4j
@RequiredArgsConstructor
public class CompilationAdminServiceImpl implements CompilationAdminService {

    private final CompilationRepository compilationRepository;
    private final EventPublicService eventPublicService;
    @Override
    public CompilationDto create(NewCompilationDto dto) {
        Set<Event> events = (Set<Event>) eventPublicService.findAllByIdIn(dto.getEvents());
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
            throw new ForbiddenException("already exist");
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
            throw new ForbiddenException("not exist");
        }
        events.remove(event);
        compilation.setEvents(events);
        compilationRepository.save(compilation);
    }
}
