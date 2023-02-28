package ru.practikum.ewm.general.service.publicAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practikum.ewm.general.model.Compilation;
import ru.practikum.ewm.general.model.dto.CompilationDto;
import ru.practikum.ewm.general.model.dto.EventShortDto;
import ru.practikum.ewm.general.model.mapper.CompilationMapper;
import ru.practikum.ewm.general.model.mapper.EventMapper;
import ru.practikum.ewm.general.pagination.OffsetPageable;
import ru.practikum.ewm.general.repository.CompilationRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/* TODO add extra data to event: confirmedRequest and views */

@Service
@Slf4j
@RequiredArgsConstructor
public class CompilationPublicServiceImpl implements CompilationPublicService {

    private final CompilationRepository compilationRepository;

    @Override
    public CompilationDto get(Long compId) {
        Compilation compilation = compilationRepository.get(compId);
        List<EventShortDto> shortDtoList = getShortDtoList(compilation);
        return CompilationMapper.toDto(compilation, shortDtoList);
    }

    @Override
    public Collection<CompilationDto> getAll(Boolean pinned, Integer from, Integer size) {
        Page<Compilation> compilations = compilationRepository.findByPinned(pinned,
                OffsetPageable.of(from, size, Sort.unsorted()));
        return compilations.stream()
                .map(compilation -> CompilationMapper.toDto(compilation, getShortDtoList(compilation)))
                .collect(Collectors.toList());
    }

    private List<EventShortDto> getShortDtoList(Compilation compilation) {
        return compilation.getEvents()
                .stream()
                .map(event -> EventMapper.toShortDto(event, 0L, 0L))
                .collect(Collectors.toList());
    }
}
