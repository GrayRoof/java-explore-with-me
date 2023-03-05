package ru.practikum.ewm.general.models.mappers;

import ru.practikum.ewm.general.models.Compilation;
import ru.practikum.ewm.general.models.Event;
import ru.practikum.ewm.general.models.dto.CompilationDto;
import ru.practikum.ewm.general.models.dto.EventShortDto;
import ru.practikum.ewm.general.models.dto.NewCompilationDto;

import java.util.List;
import java.util.Set;

public class CompilationMapper {

    private CompilationMapper() {
    }

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
