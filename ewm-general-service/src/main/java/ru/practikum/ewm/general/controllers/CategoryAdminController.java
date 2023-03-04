package ru.practikum.ewm.general.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practikum.ewm.general.models.dto.CategoryDto;
import ru.practikum.ewm.general.models.dto.NewCategoryDto;
import ru.practikum.ewm.general.services.adminAPI.CategoryAdminService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController {
    private final CategoryAdminService service;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@Valid @RequestBody NewCategoryDto dto) {
        return service.createCategory(dto);
    }

    @PatchMapping("/{id}")
    public CategoryDto updateCategory(@Valid @RequestBody CategoryDto dto,
                                      @PathVariable Long id) {
        return service.updateCategory(dto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable long id) {
        service.deleteCategory(id);
    }

}
