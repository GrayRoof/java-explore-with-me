package ru.practikum.ewm.general.models.mappers;

import ru.practikum.ewm.general.models.Category;
import ru.practikum.ewm.general.models.dto.CategoryDto;
import ru.practikum.ewm.general.models.dto.NewCategoryDto;

public class CategoryMapper {
    private CategoryMapper() {
    }

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
