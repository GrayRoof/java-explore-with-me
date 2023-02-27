package ru.practikum.ewm.general.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practikum.ewm.general.model.dto.CompilationDto;
import ru.practikum.ewm.general.model.dto.NewCompilationDto;
import ru.practikum.ewm.general.service.adminAPI.CompilationAdminService;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
public class CompilationAdminController {

    private final CompilationAdminService compilationAdminService;

    @PostMapping()
    public CompilationDto create(@RequestBody NewCompilationDto dto) {
        return compilationAdminService.create(dto);
    }

    @DeleteMapping("{compId}")
    public void delete(@PathVariable @Positive long compilationId) {
        compilationAdminService.delete(compilationId);
    }

    @DeleteMapping("{compId}/pin")
    public void deletePin(@PathVariable @Positive long compilationId) {
        compilationAdminService.deletePin(compilationId);
    }

    @PatchMapping("{compId}/pin")
    public void pinned(@PathVariable @Positive long compilationId) {
        compilationAdminService.pinned(compilationId);
    }

    @DeleteMapping("{compId}/events/{eventId}")
    public void deleteEvent(@PathVariable @Positive long compilationId, @PathVariable @Positive long eventId) {
        compilationAdminService.deleteEvent(compilationId, eventId);
    }

    @PatchMapping("{compId}/events/{eventId}")
    public void addEvent(@PathVariable @Positive long compilationId, @PathVariable @Positive long eventId) {
        compilationAdminService.addEvent(compilationId, eventId);
    }
}
