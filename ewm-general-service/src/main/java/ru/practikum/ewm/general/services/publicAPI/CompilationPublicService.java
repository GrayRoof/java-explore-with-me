package ru.practikum.ewm.general.services.publicAPI;

import ru.practikum.ewm.general.models.dto.CompilationDto;

import java.util.Collection;

public interface CompilationPublicService {
    CompilationDto get(Long compId);

    Collection<CompilationDto> getAll(Boolean pinned, Integer from, Integer size);
}
