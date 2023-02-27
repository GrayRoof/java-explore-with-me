package ru.practikum.ewm.general.service.privateAPI;

import ru.practikum.ewm.general.model.dto.RequestDto;

import java.util.Collection;

public class RequestPrivateServiceImpl implements RequestPrivateService {

    @Override
    public Collection<RequestDto> findAllByRequesterId(long userId) {
        return null;
    }

    @Override
    public RequestDto create(long userId, long eventId) {
        return null;
    }

    @Override
    public RequestDto cancel(long userId, long requestId) {
        return null;
    }
}
