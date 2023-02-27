package ru.practikum.ewm.general.model.mapper;

import ru.practikum.ewm.general.model.Category;
import ru.practikum.ewm.general.model.dto.CategoryDto;
import ru.practikum.ewm.general.model.dto.NewCategoryDto;

public class CategoryMapper {
    public static Category toCategory(NewCategoryDto dto) {

        Category category = new Category();
        category.setName(dto.getName());
        return category;
    }

    public static Category toCategory(CategoryDto dto) {
        return new Category(dto.getId(), dto.getName());
    }

    public static CategoryDto toDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }
}
