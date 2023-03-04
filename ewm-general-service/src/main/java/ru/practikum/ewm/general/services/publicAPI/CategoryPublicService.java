package ru.practikum.ewm.general.services.publicAPI;

import ru.practikum.ewm.general.models.Category;
import ru.practikum.ewm.general.models.dto.CategoryDto;

import java.util.Collection;

public interface CategoryPublicService {

    Collection<CategoryDto> getAll(Integer from, Integer size);

    CategoryDto get(long id);

    Category getEntity(long id);
}
