package ru.practikum.ewm.general.service.adminAPI;

import ru.practikum.ewm.general.model.dto.CompilationDto;
import ru.practikum.ewm.general.model.dto.NewCompilationDto;
import ru.practikum.ewm.general.model.dto.UpdateCompilationDto;

public interface CompilationAdminService {
    CompilationDto create(NewCompilationDto dto);

    void pinned(long compilationId);

    void addEvent(long compilationId, long eventId);

    void delete(long compilationId);

    void deletePin(long compilationId);

    void deleteEvent(long compilationId, long eventId);

    CompilationDto update(long compilationId, UpdateCompilationDto dto);
}
