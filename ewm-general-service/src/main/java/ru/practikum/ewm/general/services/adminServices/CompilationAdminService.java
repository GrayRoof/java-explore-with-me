package ru.practikum.ewm.general.services.adminServices;

import ru.practikum.ewm.general.models.dto.CompilationDto;
import ru.practikum.ewm.general.models.dto.NewCompilationDto;
import ru.practikum.ewm.general.models.dto.UpdateCompilationDto;

public interface CompilationAdminService {
    CompilationDto create(NewCompilationDto dto);

    void pinned(long compilationId);

    void addEvent(long compilationId, long eventId);

    void delete(long compilationId);

    void deletePin(long compilationId);

    void deleteEvent(long compilationId, long eventId);

    CompilationDto update(long compilationId, UpdateCompilationDto dto);
}
