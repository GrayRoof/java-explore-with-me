package ru.practikum.ewm.general.model.mapper;

import ru.practikum.ewm.general.model.Compilation;
import ru.practikum.ewm.general.model.Event;
import ru.practikum.ewm.general.model.dto.CompilationDto;
import ru.practikum.ewm.general.model.dto.EventShortDto;
import ru.practikum.ewm.general.model.dto.NewCompilationDto;

import java.util.List;
import java.util.Set;

public class CompilationMapper {

    public static CompilationDto toDto(Compilation compilation, List<EventShortDto> eventShortDtoes) {
        CompilationDto compilationDto = new CompilationDto();
        compilationDto.setId(compilation.getId());
        compilationDto.setPinned(compilation.isPinned());
        compilationDto.setTitle(compilation.getTitle());
        compilationDto.setEvents(eventShortDtoes);
        return compilationDto;
    }

    public static Compilation toCompilation(NewCompilationDto compilationDto, Set<Event> events) {
        Compilation compilation = new Compilation();
        compilation.setPinned(compilationDto.getPinned());
        compilation.setTitle(compilationDto.getTitle());
        compilation.setEvents(events);
        return compilation;
    }
}
