package com.rest_library.controller;

import com.google.gson.Gson;
import com.rest_library.dto.TitleDto;
import com.rest_library.entity.Title;
import com.rest_library.service.TitleServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TitleController.class)
public class TitleControllerTest {

    private TitleDto testTitleDto1;
    private TitleDto testTitleDto2;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TitleServiceImpl titleServiceImpl;

    @BeforeEach
    void initialize() {
        testTitleDto1 = TitleDto.builder()
                .id(1L)
                .bookTitle("test title")
                .author("test author")
                .build();

        testTitleDto2 = TitleDto.builder()
                .id(2L)
                .bookTitle("test title2")
                .author("test author2")
                .build();
    }

    @Test
    @DisplayName("Testing createTitle() method.")
    public void givenTitleObject_whenCreateTitle_thenReturnSavedTitle() throws Exception {
        // given
        when(titleServiceImpl.saveTitle(any(TitleDto.class))).thenReturn(testTitleDto1);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(testTitleDto1);

        // when, then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/titles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookTitle", Matchers.is("test title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author", Matchers.is("test author")));
    }

    @Test
    @DisplayName("Testing findAllTitles() that should return empty List.")
    public void givenEmptyTitlesList_whenFindAllTitles_thenReturnEmptyList() throws Exception {
        // given
        when(titleServiceImpl.findAllTitles()).thenReturn(List.of());

        // when, then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/v1/titles/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    @DisplayName("Testing findAllTitles() method.")
    public void givenTitleList_whenFindAllTitles_thenReturnTitleList() throws Exception {
        // given
        when(titleServiceImpl.findAllTitles()).thenReturn(List.of(testTitleDto1, testTitleDto2));

        // when, then
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/titles/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bookTitle", Matchers.is("test title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author", Matchers.is("test author")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].bookTitle", Matchers.is("test title2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].author", Matchers.is("test author2")));
    }

    @Test
    @DisplayName("Testing findTitleById(@PathVariable(id) Long id) method.")
    public void givenTitleDtoId_whenFindTitleById_thenReturnTitleDtoObject() throws Exception {
        // given
        Long id = testTitleDto1.getId();
        when(titleServiceImpl.findTitleById(id)).thenReturn(testTitleDto1);

        // when, then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/v1/titles/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookTitle", Matchers.is("test title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author", Matchers.is("test author")));
    }

    @Test
    @DisplayName("Testing deleteTitle(@PathVariable(id) Long id) method.")
    public void givenTitleId_whenDeleteTitle_thenTitleDeleted() throws Exception {
        // given
        Long id = testTitleDto1.getId();
        willDoNothing().given(titleServiceImpl).deleteTitle(id);

        // when, then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/api/v1/titles/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
