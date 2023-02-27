package ru.practikum.ewm.general.service.publicAPI;

import ru.practikum.ewm.general.model.dto.CompilationDto;

import java.util.Collection;

public class CompilationPublicServiceImpl implements CompilationPublicService {

    @Override
    public CompilationDto findById(Long compId) {
        return null;
    }

    @Override
    public Collection<CompilationDto> findAll(Boolean pinned, Integer from, Integer size) {
        return null;
    }
}
