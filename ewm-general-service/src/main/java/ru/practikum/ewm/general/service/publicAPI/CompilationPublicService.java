package ru.practikum.ewm.general.service.publicAPI;

import ru.practikum.ewm.general.model.dto.CompilationDto;

import java.util.Collection;

public interface CompilationPublicService {
    CompilationDto get(Long compId);

    Collection<CompilationDto> getAll(Boolean pinned, Integer from, Integer size);
}
