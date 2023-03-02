package ru.practikum.ewm.general.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CategoryDto {
    private long id;
    @NotNull
    @NotBlank
    private String name;
}
