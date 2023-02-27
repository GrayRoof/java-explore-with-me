package ru.practikum.ewm.general.service.privateAPI;

import ru.practikum.ewm.general.model.dto.RequestDto;

import java.util.Collection;

public interface RequestPrivateService {

    RequestDto create(long userId, long eventId);

    RequestDto cancel(long userId, long requestId);

    Collection<RequestDto> findAllByRequesterId(long userId);
}
