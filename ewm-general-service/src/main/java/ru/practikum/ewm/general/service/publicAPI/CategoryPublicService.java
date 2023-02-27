package ru.practikum.ewm.general.service.publicAPI;

import ru.practikum.ewm.general.model.dto.CategoryDto;

import java.util.Collection;

public interface CategoryPublicService {

    Collection<CategoryDto> getAll(Integer from, Integer size);

    CategoryDto get(Long id);
}
