package ru.practikum.ewm.general.service.publicAPI;

import ru.practikum.ewm.general.model.dto.CompilationDto;

import java.util.Collection;

public interface CompilationPublicService {
    CompilationDto findById(Long compId);

    Collection<CompilationDto> findAll(Boolean pinned, Integer from, Integer size);
}
