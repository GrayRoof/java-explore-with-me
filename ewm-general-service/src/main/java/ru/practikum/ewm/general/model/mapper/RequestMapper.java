package ru.practikum.ewm.general.model.mapper;

import ru.practikum.ewm.general.model.ParticipationRequest;
import ru.practikum.ewm.general.model.dto.RequestDto;

import java.time.format.DateTimeFormatter;

public class RequestMapper {

    public static RequestDto toDto(ParticipationRequest request) {
        RequestDto requestDto = new RequestDto();
        requestDto.setId(request.getId());
        requestDto.setRequester(request.getRequester().getId());
        requestDto.setEvent(request.getEvent().getId());
        requestDto.setCreated(request.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        requestDto.setStatus(request.getStatus());
        return requestDto;
    }

    public static ParticipationRequest toRequest(RequestDto dto) {
        return null;
    }
}
