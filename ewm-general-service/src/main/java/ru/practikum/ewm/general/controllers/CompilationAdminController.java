package ru.practikum.ewm.general.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practikum.ewm.general.models.dto.CompilationDto;
import ru.practikum.ewm.general.models.dto.NewCompilationDto;
import ru.practikum.ewm.general.models.dto.UpdateCompilationDto;
import ru.practikum.ewm.general.services.adminServices.CompilationAdminService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
public class CompilationAdminController {

    private final CompilationAdminService compilationAdminService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto create(@Valid @RequestBody NewCompilationDto dto) {
        return compilationAdminService.create(dto);
    }

    @PatchMapping("{compilationId}")
    public CompilationDto update(@PathVariable @Positive long compilationId, @RequestBody UpdateCompilationDto dto) {
        return compilationAdminService.update(compilationId, dto);
    }

    @DeleteMapping("{compilationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Positive long compilationId) {
        compilationAdminService.delete(compilationId);
    }

    @DeleteMapping("{compilationId}/pin")
    public void deletePin(@PathVariable @Positive long compilationId) {
        compilationAdminService.deletePin(compilationId);
    }

    @PatchMapping("{compilationId}/pin")
    public void pinned(@PathVariable @Positive long compilationId) {
        compilationAdminService.pinned(compilationId);
    }

    @DeleteMapping("{compilationId}/events/{eventId}")
    public void deleteEvent(@PathVariable @Positive long compilationId, @PathVariable @Positive long eventId) {
        compilationAdminService.deleteEvent(compilationId, eventId);
    }

    @PatchMapping("{compilationId}/events/{eventId}")
    public void addEvent(@PathVariable @Positive long compilationId, @PathVariable @Positive long eventId) {
        compilationAdminService.addEvent(compilationId, eventId);
    }
}
