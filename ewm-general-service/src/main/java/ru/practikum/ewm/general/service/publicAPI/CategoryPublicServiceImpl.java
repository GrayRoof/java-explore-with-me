package ru.practikum.ewm.general.service.publicAPI;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practikum.ewm.general.model.CategoryMapper;
import ru.practikum.ewm.general.model.dto.CategoryDto;
import ru.practikum.ewm.general.pagination.OffsetPageable;
import ru.practikum.ewm.general.repository.CategoryRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryPublicServiceImpl implements CategoryPublicService {

    private final CategoryRepository categoryRepository;

    @Override
    public Collection<CategoryDto> findAllCategories(Integer from, Integer size) {
        return categoryRepository.findAll(OffsetPageable.of(from, size, Sort.unsorted()))
                .stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto findCategoryById(Long id) {
        return CategoryMapper.toDto(categoryRepository.get(id));
    }
}
