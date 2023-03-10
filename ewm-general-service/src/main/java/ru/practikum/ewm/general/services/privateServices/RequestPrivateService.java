package ru.practikum.ewm.general.services.privateServices;

import ru.practikum.ewm.general.models.dto.RequestDto;

import java.util.Collection;
import java.util.List;

public interface RequestPrivateService {

    Collection<RequestDto> findAllByRequesterId(long userId);

    Collection<RequestDto> findAllByEvent(long id);

    RequestDto create(long userId, long eventId);

    RequestDto cancel(long userId, long requestId);

    int getCountConfirmedForEvent(long eventId);

    Collection<RequestDto> rejectRequests(List<Long> ids);

    Collection<RequestDto> confirmRequests(List<Long> ids);
}
