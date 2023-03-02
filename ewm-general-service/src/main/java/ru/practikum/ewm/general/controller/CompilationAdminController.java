package ru.practikum.ewm.general.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practikum.ewm.general.model.dto.CompilationDto;
import ru.practikum.ewm.general.model.dto.NewCompilationDto;
import ru.practikum.ewm.general.service.adminAPI.CompilationAdminService;

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
