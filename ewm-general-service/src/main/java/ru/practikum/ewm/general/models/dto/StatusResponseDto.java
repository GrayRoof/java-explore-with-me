package ru.practikum.ewm.general.models.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class StatusResponseDto {
    Collection<RequestDto> confirmedRequests;
    Collection<RequestDto> rejectedRequests;
}
