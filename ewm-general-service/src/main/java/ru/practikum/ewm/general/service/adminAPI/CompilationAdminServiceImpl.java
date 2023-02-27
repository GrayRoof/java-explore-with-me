package ru.practikum.ewm.general.service.adminAPI;

import ru.practikum.ewm.general.model.dto.CompilationDto;
import ru.practikum.ewm.general.model.dto.NewCompilationDto;

public class CompilationAdminServiceImpl implements CompilationAdminService {
    @Override
    public CompilationDto create(NewCompilationDto dto) {
        return null;
    }

    @Override
    public void pinned(long compilationId) {

    }

    @Override
    public void addEvent(long compilationId, long eventId) {

    }

    @Override
    public void delete(long compilationId) {

    }

    @Override
    public void deletePin(long compilationId) {

    }

    @Override
    public void deleteEvent(long compilationId, long eventId) {

    }
}
