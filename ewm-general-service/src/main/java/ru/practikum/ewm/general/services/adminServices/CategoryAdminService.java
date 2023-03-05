package ru.practikum.ewm.general.services.adminServices;

import ru.practikum.ewm.general.models.dto.CategoryDto;
import ru.practikum.ewm.general.models.dto.NewCategoryDto;

public interface CategoryAdminService {
    CategoryDto createCategory(NewCategoryDto dto);

    CategoryDto updateCategory(CategoryDto dto, long categoryId);

    void deleteCategory(long id);
}
