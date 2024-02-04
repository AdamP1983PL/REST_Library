package com.rest_library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rest_library.dto.ReaderDto;
import com.rest_library.entity.Reader;
import com.rest_library.mapper.ReaderMapper;
import com.rest_library.service.ReaderService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
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
@ExtendWith(MockitoExtension.class)
class ReaderControllerTest {

    private Reader testReader;
    private ReaderDto testReaderDto;
    private Reader testReader2;
    private ReaderDto testReaderDto2;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReaderService readerService;

    @MockBean
    private ReaderMapper readerMapper;

    @MockBean
    private ReaderController readerController;

    @Autowired
    private ObjectMapper objectMapper;

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

        testReader2 = Reader.builder()
                .id(2L)
                .email("test2@test.com")
                .firstName("test first name2")
                .lastName("test last name2")
                .startingDate(LocalDate.of(2222, 2, 2))
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
        System.out.println("\n\n\tjsonContent ====>>>>" + jsonContent + "\n\n");

        // when, then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/readers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

//                .andExpect(MockMvcResultMatchers.jsonPath("$.id",
//                        Matchers.is("1L")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.email",
//                        Matchers.is("test@test.com")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
//                        Matchers.is("test first name")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",
//                        Matchers.is("test last name")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.startingDate",
//                        Matchers.is(LocalDate.of(1111, 1, 1))));
    }

//    @Test
//    @DisplayName("Testing createReader() method.")
//    public void givenReaderObject_whenCreateReader_thenReturnSavedReader() throws Exception {
//        // given
//        BDDMockito.given(readerService.saveReader(ArgumentMatchers.any(ReaderDto.class)))
//                .willAnswer((invocation) -> invocation.getArgument(0));
//
//        // when
//        ResultActions response =  mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/readers")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(testReaderDto)));
//
//        // then
//        System.out.println("====>>>>Response Body: " + response.andReturn().getResponse().getContentAsString());
//
//        response.andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.email",
//                        CoreMatchers.is(testReaderDto.getEmail())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
//                        CoreMatchers.is(testReaderDto.getFirstName())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",
//                        CoreMatchers.is(testReaderDto.getLastName())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.startingDate",
//                        CoreMatchers.is(testReaderDto.getStartingDate())));
//    }


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
        List<ReaderDto> readersDto = new ArrayList<>(List.of(testReaderDto, testReaderDto2));
        BDDMockito.when(readerService.findAllReaders()).thenReturn(readersDto);

        // when, then
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/readers/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Matchers.is("test@test.com")));

        System.out.println("\n\n\t====>>>> response: " + response + "\n\n");

    }


}
// todo refactor the code, use static method
