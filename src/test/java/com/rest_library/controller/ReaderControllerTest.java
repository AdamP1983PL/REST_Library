package com.rest_library.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rest_library.dto.ReaderDto;
import com.rest_library.entity.Reader;
import com.rest_library.service.ReaderServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;


/*SpringBoot provides @WebMvcTest annotation to test Spring MVC Controller.
 * Also, @WebMvcTest based tests run faster as it will load only the specific controller
 * and its dependencies only without loading the entire application
 *
 * Spring Boot instantiates only the web layer rather than the whole application context.
 * In an application with multiple controllers, we can even ask for only one to be
 * instantiated by using, for example "@WebMvcTest(ReaderController.class)"
 *
 * Spring Boot provides @WebMvcTest annotation to test Spring MVC controllers.
 * This annotation creates an application context that contains all the beans necessary for
 * testing a Spring web controller.
 *
 * Spring Boot provides @SpringBootTest annotation for Integration testing.
 * This annotation creates an application context and loads full application context
 *
 * Unit Testing - @WebMvcTest annotation
 * Integration testing - @SpringBootTest*/

@SpringJUnitWebConfig
@WebMvcTest(ReaderController.class)
public class ReaderControllerTest {

    private Reader testReader;
    private ReaderDto testReaderDto;
    private ReaderDto testReaderDto2;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReaderServiceImpl readerService;

    @BeforeEach
    void initialize() {
        testReader = Reader.builder()
                .id(1L)
                .email("test@test.com")
                .firstName("test first name")
                .lastName("test last name")
                .startingDate(LocalDate.of(1111, 1, 1))
                .build();

        testReaderDto = ReaderDto.builder()
                .id(1L)
                .email("test@test.com")
                .firstName("test first name")
                .lastName("test last name")
                .startingDate(LocalDate.of(1111, 1, 1))
                .build();

        testReaderDto2 = ReaderDto.builder()
                .id(2L)
                .email("test2@test.com")
                .firstName("test first name2")
                .lastName("test last name2")
                .startingDate(LocalDate.of(2222, 2, 2))
                .build();
    }

    @Test
    @DisplayName("Testing createReader() method.")
    public void givenReaderObject_whenCreateReader_thenReturnSavedReader() throws Exception {
        // given
        when(readerService.saveReader(any(ReaderDto.class))).thenReturn(testReaderDto);
//        https://stackoverflow.com/questions/72769462/failed-making-field-property-
//        accessible-either-change-its-visibility-or-write
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        String jsonContent = gson.toJson(testReaderDto);

        // when, then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/readers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("test@test.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("test first name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("test last name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startingDate", Matchers.is("1111-01-01")));
    }

    @Test
    @DisplayName("Testing findAllReaders() that should return empty List.")
    public void givenEmptyReadersList_whenFindAllReaders_thenReturnEmptyList() throws Exception {
        // given
        when(readerService.findAllReaders()).thenReturn(List.of());

        // when, then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/v1/readers/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    @DisplayName("Testing findAllReaders() method.")
    public void givenListOfReaders_whenFindAllReaders_thenReturnReadersList() throws Exception {
        // given
        when(readerService.findAllReaders()).thenReturn(List.of(testReaderDto, testReaderDto2));

        // when, then
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/readers/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Matchers.is("test@test.com")));
    }

    @Test
    @DisplayName("Testing findReaderById(@PathVariable(id) Long id) method.")
    public void givenReadersDtoId_whenFindReaderById_thenReturnReaderDtoObject() throws Exception {
        // given
        when(readerService.findReader(testReaderDto.getId())).thenReturn(testReaderDto);

        // when, then
        ResultActions response = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/v1/readers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("test@test.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("test first name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("test last name")));
//                .andExpect(MockMvcResultMatchers
//                        .jsonPath("$.startingDate", Matchers.is(LocalDate.of(1111, 01, 01))));

        String dateString = response.andReturn().getResponse().getContentAsString();
        JsonNode jsonNode = new ObjectMapper().readTree(dateString);
        String actualDateString = jsonNode.get("startingDate").asText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate actualDate = LocalDate.parse(actualDateString, formatter);

        Assertions.assertEquals(actualDate, LocalDate.of(1111, 1, 1));
    }

    @Test
    @DisplayName("Testing deleteReader(@PathVariable(id) Long id) method. ")
    public void givenReaderObject_whenDeleteReader_thenReaderDeleted() throws Exception {
        // given
        Long id = testReader.getId();
        willDoNothing().given(readerService).deleteReader(id);

        // when, then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/api/v1/readers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
