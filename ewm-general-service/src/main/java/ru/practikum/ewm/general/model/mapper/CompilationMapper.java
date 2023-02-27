package ru.practikum.ewm.general.model.mapper;

import ru.practikum.ewm.general.model.Compilation;
import ru.practikum.ewm.general.model.Event;
import ru.practikum.ewm.general.model.dto.CompilationDto;
import ru.practikum.ewm.general.model.dto.EventFullDto;
import ru.practikum.ewm.general.model.dto.EventShortDto;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CompilationMapper {

    public static CompilationDto toDto(Compilation compilation, List<EventShortDto> eventShortDtos) {
        CompilationDto compilationDto = new CompilationDto();
        compilationDto.setId(compilation.getId());
        compilationDto.setPinned(compilation.isPinned());
        compilationDto.setTitle(compilation.getTitle());
        compilationDto.setEvents(eventShortDtos);
        return compilationDto;
    }

    public static Compilation toCompilation(CompilationDto compilationDto, Set<Event> events) {
        Compilation compilation = new Compilation();
        compilation.setId(compilationDto.getId());
        compilation.setPinned(compilationDto.isPinned());
        compilation.setTitle(compilationDto.getTitle());
        compilation.setEvents(events);
        return compilation;
    }
}
