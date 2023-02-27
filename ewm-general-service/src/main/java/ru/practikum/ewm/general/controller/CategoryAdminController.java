package ru.practikum.ewm.general.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practikum.ewm.general.model.dto.CategoryDto;
import ru.practikum.ewm.general.model.dto.NewCategoryDto;
import ru.practikum.ewm.general.service.adminAPI.CategoryAdminService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController {
    private final CategoryAdminService service;

    @PostMapping()
    public CategoryDto createCategory(@Valid @RequestBody NewCategoryDto dto) {
        return service.createCategory(dto);
    }

    @PatchMapping()
    public CategoryDto updateCategory(@Valid @RequestBody CategoryDto dto) {
        return service.updateCategory(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable long id) {
        service.deleteCategory(id);
    }

}
