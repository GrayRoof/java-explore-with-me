package ru.practikum.ewm.general.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practikum.ewm.general.models.dto.CategoryDto;
import ru.practikum.ewm.general.services.adminServices.CategoryAdminService;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryAdminController.class)
class CategoryAdminControllerTest {

    @MockBean
    private CategoryAdminService categoryAdminService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    CategoryDto categoryDto;
    CategoryDto categoryToInputDto;

    @BeforeEach
    void setUp() {
        categoryDto = new CategoryDto();
        categoryDto.setId(1);
        categoryDto.setName("first");

        categoryToInputDto = new CategoryDto();
        categoryToInputDto.setName("first");
    }

    @Test
    void createCategory() throws Exception {
        when(categoryAdminService.createCategory(any()))
                .thenReturn(categoryDto);
        mvc.perform(post("/admin/categories")
                    .content(mapper.writeValueAsString(categoryToInputDto))
                    .characterEncoding(StandardCharsets.UTF_8)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(categoryDto.getId()), Long.class));
    }

    @Test
    void updateCategory() throws Exception {
        categoryDto.setName("updated");
        when(categoryAdminService.updateCategory(any(), anyLong()))
                .thenReturn(categoryDto);
        mvc.perform(patch("/admin/categories/1")
                    .content(mapper.writeValueAsString(categoryToInputDto))
                    .characterEncoding(StandardCharsets.UTF_8)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(categoryDto.getName())));
    }

    @Test
    void deleteCategory() throws Exception {
        doNothing().when(categoryAdminService).deleteCategory(anyLong());
        mvc.perform(delete("/admin/categories/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}