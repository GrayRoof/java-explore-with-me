package ru.practikum.ewm.general.services.publicAPI;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practikum.ewm.general.models.Category;
import ru.practikum.ewm.general.models.mappers.CategoryMapper;
import ru.practikum.ewm.general.models.dto.CategoryDto;
import ru.practikum.ewm.general.paginations.OffsetPageable;
import ru.practikum.ewm.general.repositories.CategoryRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryPublicServiceImpl implements CategoryPublicService {

    private final CategoryRepository categoryRepository;

    @Override
    public Collection<CategoryDto> getAll(Integer from, Integer size) {

        return categoryRepository.findAll(OffsetPageable.of(from, size, Sort.by(Sort.Direction.ASC, "id")))
                .stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto get(long id) {
        return CategoryMapper.toDto(getEntity(id));
    }

    @Override
    public Category getEntity(long id) {
        return categoryRepository.get(id);
    }
}
