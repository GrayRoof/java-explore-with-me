package ru.practikum.ewm.general.model.dto;

import lombok.Data;
import ru.practikum.ewm.general.model.RequestStatus;

import java.util.List;

@Data
public class StatusRequestDto {
    List<Long> requestIds;
    RequestStatus status;
}
