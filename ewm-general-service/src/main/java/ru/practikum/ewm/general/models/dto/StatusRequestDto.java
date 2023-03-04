package ru.practikum.ewm.general.models.dto;

import lombok.Data;
import ru.practikum.ewm.general.models.enums.RequestStatus;

import java.util.List;

@Data
public class StatusRequestDto {
    List<Long> requestIds;
    RequestStatus status;
}
