package ru.practikum.ewm.general.service.adminAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practikum.ewm.general.model.Category;
import ru.practikum.ewm.general.model.dto.CategoryDto;
import ru.practikum.ewm.general.model.dto.NewCategoryDto;
import ru.practikum.ewm.general.model.mapper.CategoryMapper;
import ru.practikum.ewm.general.repository.CategoryRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryAdminServiceImpl implements CategoryAdminService {

    private final CategoryRepository categoryRepository;
    @Override
    public CategoryDto createCategory(NewCategoryDto dto) {
        CategoryDto categoryDto = CategoryMapper.toDto(categoryRepository
                .save(CategoryMapper.toCategory(dto)));
        log.info("Категория {} с id = {} была создана", categoryDto.getName(), categoryDto.getId());
        return categoryDto;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto dto) {
        Category category = categoryRepository.get(dto.getId());
        category.setName(dto.getName());
        CategoryDto categoryDto = CategoryMapper.toDto(categoryRepository.save(category));
        log.info("Категория с id = {} была обновлена, новое имя - {}", categoryDto.getId(), categoryDto.getName());
        return categoryDto;
    }

    @Override
    public void deleteCategory(long id) {
        Category category = categoryRepository.get(id);
        categoryRepository.delete(category);
        log.info("Категория с id {} была удалена", id);
    }
}
