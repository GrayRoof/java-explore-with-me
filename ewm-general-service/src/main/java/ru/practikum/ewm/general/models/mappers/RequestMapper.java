package ru.practikum.ewm.general.models.mappers;

import ru.practikum.ewm.general.models.ParticipationRequest;
import ru.practikum.ewm.general.models.dto.RequestDto;

import java.time.format.DateTimeFormatter;

public class RequestMapper {

    private RequestMapper() {
    }

    public static RequestDto toDto(ParticipationRequest request) {
        RequestDto requestDto = new RequestDto();
        requestDto.setId(request.getId());
        requestDto.setRequester(request.getRequester().getId());
        requestDto.setEvent(request.getEvent().getId());
        requestDto.setCreated(request.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        requestDto.setStatus(request.getStatus());
        return requestDto;
    }
}
