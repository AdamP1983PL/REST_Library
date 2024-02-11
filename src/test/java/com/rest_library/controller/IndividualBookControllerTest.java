package com.rest_library.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rest_library.dto.IndividualBookDto;
import com.rest_library.dto.IndividualBookPostDto;
import com.rest_library.entity.Title;
import com.rest_library.enums.Status;
import com.rest_library.service.IndividualBookServiceImpl;
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
@WebMvcTest(IndividualBookController.class)
class IndividualBookControllerTest {

    private Title testTitle1;
    private IndividualBookDto individualBookDto1;
    private IndividualBookDto individualBookDto2;
    private IndividualBookPostDto individualBookPostDto1;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IndividualBookServiceImpl individualBookService;

    @MockBean
    private TitleServiceImpl titleService;

    @BeforeEach
    void initialize() {
        individualBookDto1 = IndividualBookDto.builder()
                .id(1L)
                .individualBookTitle("test title")
                .status(Status.AVAILABLE)
                .build();

        individualBookDto2 = IndividualBookDto.builder()
                .id(2L)
                .individualBookTitle("test title2")
                .status(Status.IN_CIRCULATION)
                .build();

        individualBookPostDto1 = IndividualBookPostDto.builder()
                .id(1L)
                .title(testTitle1)
                .status(Status.AVAILABLE)
                .build();

        testTitle1 = Title.builder()
                .id(1L)
                .bookTitle("test title")
                .author("test author")
                .publicationYear(2024)
                .build();
    }

    @Test
    @DisplayName("Testing createIndividualBook(@RequestBody IndividualBookDto indBook) method.")
    public void givenIndividualBookDtoObject_whenCreateIndividualBook_thenReturnSavedIndividualBookDto()
            throws Exception {
        // given
        when(individualBookService.createIndividualBook(any(IndividualBookDto.class)))
                .thenReturn(individualBookDto1);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(individualBookDto1);

        // when, then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/individual-books/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTH-8")
                        .content(jsonContent))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.individualBookTitle", Matchers.is("test title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("AVAILABLE")));
    }

    @Test
    @DisplayName("Testing createIndividualBook(@RequestBody IndividualBookPostDto individualBookPostDto) method.")
    public void givenIndividualBookPostDto_whenCreateIndividualBook_thenThenReturnSavedIndividualBookPostDto()
            throws Exception {
        // given
        when(individualBookService.createIndividualBook(any(IndividualBookPostDto.class)))
                .thenReturn(individualBookPostDto1);
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        String jsonContent = gson.toJson(individualBookPostDto1);

        // when, then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/individual-books/ibpdto/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTH-8")
                        .content(jsonContent))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("AVAILABLE")));
    }

    @Test
    @DisplayName("Testing findAllIndividualBooks() method.")
    public void givenIndividualBookDtosList_whenFindAllBooks_thenReturnIndividualBookDtosList() throws Exception {
        // given
        when(individualBookService.findAllIndividualBooks())
                .thenReturn(List.of(individualBookDto1, individualBookDto2));

        // when, then
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/individual-books/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$[0].individualBookTitle", Matchers.is("test title")))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$[0].status", Matchers.is("AVAILABLE")))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$[1].individualBookTitle", Matchers.is("test title2")))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$[1].status", Matchers.is("IN_CIRCULATION")));
    }

    @Test
    @DisplayName("Testing findIndividualBookById(@PathVariable(id) Long id) method.")
    public void givenIndividualBookDtoId_whenFindIndividualBookById_thenReturnIndividualBookObject() throws Exception {
        // given
        Long id = individualBookDto1.getId();
        when(individualBookService.findIndividualBookById(id)).thenReturn(individualBookDto1);

        // when, then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/v1/individual-books/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.individualBookTitle", Matchers.is("test title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("AVAILABLE")));
    }

    @Test
    @DisplayName("Testing deleteIndividualBook(@PathVariable(id) method.")
    public void givenIndividualBookDtoId_whenDeleteIndividualBook_thenIndividualBookDeleted() throws Exception {
        // given
        Long id = individualBookDto1.getId();
        willDoNothing().given(individualBookService).deleteIndividualBook(id);

        // when, then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/api/v1/individual-books/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
