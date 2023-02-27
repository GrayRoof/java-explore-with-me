package ru.practikum.ewm.general.service.privateAPI;

import ru.practikum.ewm.general.model.dto.RequestDto;

import java.util.Collection;

public interface RequestPrivateService {

    Collection<RequestDto> findAllByRequesterId(long userId);

    RequestDto create(long userId, long eventId);

    RequestDto cancel(long userId, long requestId);
}
