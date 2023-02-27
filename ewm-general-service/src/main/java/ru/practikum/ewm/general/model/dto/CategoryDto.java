package ru.practikum.ewm.general.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class CategoryDto {
    @Positive
    private long id;
    @NotNull
    @NotBlank
    private String name;
}
