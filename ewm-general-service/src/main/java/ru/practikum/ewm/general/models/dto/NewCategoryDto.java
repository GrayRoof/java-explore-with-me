package ru.practikum.ewm.general.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class NewCategoryDto {
    @NotNull
    @NotBlank
    String name;
}
