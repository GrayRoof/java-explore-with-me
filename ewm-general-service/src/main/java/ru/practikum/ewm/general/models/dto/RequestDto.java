package ru.practikum.ewm.general.models.dto;

import lombok.Data;
import ru.practikum.ewm.general.models.enums.RequestStatus;

@Data
public class RequestDto {
    long id;
    String created;
    long event;
    long requester;
    RequestStatus status;
}
