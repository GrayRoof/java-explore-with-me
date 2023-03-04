package ru.practikum.ewm.general.services.adminAPI;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practikum.ewm.general.exceptions.NotAvailableException;
import ru.practikum.ewm.general.models.Category;
import ru.practikum.ewm.general.models.dto.CategoryDto;
import ru.practikum.ewm.general.models.dto.NewCategoryDto;
import ru.practikum.ewm.general.models.mappers.CategoryMapper;
import ru.practikum.ewm.general.repositories.CategoryRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryAdminServiceImpl implements CategoryAdminService {

    private final CategoryRepository categoryRepository;
    private final EventAdminService eventAdminService;

    @Override
    public CategoryDto createCategory(NewCategoryDto dto) {
        CategoryDto categoryDto = CategoryMapper.toDto(categoryRepository
                .save(CategoryMapper.toCategory(dto)));
        log.info("Категория {} с id = {} была создана", categoryDto.getName(), categoryDto.getId());
        return categoryDto;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto dto, long categoryId) {
        Category category = categoryRepository.get(categoryId);
        category.setName(dto.getName());
        CategoryDto categoryDto = CategoryMapper.toDto(categoryRepository.save(category));
        log.info("Категория с id = {} была обновлена, новое имя - {}", categoryDto.getId(), categoryDto.getName());
        return categoryDto;
    }

    @Override
    public void deleteCategory(long id) {
        Category category = categoryRepository.get(id);

        if (eventAdminService.getAllByCategory(category.getId()).isEmpty()) {
            categoryRepository.delete(category);
            log.info("Категория с id {} была удалена", id);
        } else {
            throw new NotAvailableException("event exists");
        }
    }
}
