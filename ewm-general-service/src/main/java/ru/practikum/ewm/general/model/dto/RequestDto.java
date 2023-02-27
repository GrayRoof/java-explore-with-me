package ru.practikum.ewm.general.model.dto;

import lombok.Data;
import ru.practikum.ewm.general.model.RequestStatus;

@Data
public class RequestDto {
    long id;
    String created;
    long event;
    long requester;
    RequestStatus status;
}
