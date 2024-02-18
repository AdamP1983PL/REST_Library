package com.rest_library.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rest_library.dto.IndividualBookDto;
import com.rest_library.dto.IndividualBookPostDto;
import com.rest_library.entity.Title;
import com.rest_library.enums.Status;
import com.rest_library.service.BooksBorrowingStatsServiceImpl;
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

import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(BooksBorrowingStatsController.class)
class BooksBorrowingStatsControllerTest {

    private Title testTitle1;
    private IndividualBookPostDto borrowedIndividualBookPostDto;
    private IndividualBookDto returnedIndividualBookDto;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BooksBorrowingStatsServiceImpl booksBorrowingStatsService;

    @BeforeEach
    void initialize() {
        testTitle1 = Title.builder()
                .id(1L)
                .bookTitle("test_title")
                .author("test author")
                .publicationYear(2024)
                .build();

        borrowedIndividualBookPostDto = IndividualBookPostDto.builder()
                .id(1L)
                .title(testTitle1)
                .status(Status.IN_CIRCULATION)
                .build();

        returnedIndividualBookDto = IndividualBookDto.builder()
                .id(2L)
                .individualBookTitle(testTitle1.getBookTitle())
                .status(Status.AVAILABLE)
                .build();
    }

    @Test
    @DisplayName("Testing borrowAvailableBook(@PathVariable(title) String title) method.")
    public void givenIndividualBookPostDto_whenBorrowAvailableBook_thenBorrowedBookStatusIsInCirculation()
            throws Exception {
        // given
        String titleString = borrowedIndividualBookPostDto.getTitle().getBookTitle();
        when(booksBorrowingStatsService.borrowAvailableBookByTitle(titleString))
                .thenReturn(borrowedIndividualBookPostDto);
//        https://stackoverflow.com/questions/72769462/failed-making-field-property-
//        accessible-either-change-its-visibility-or-write
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        String jsonContent = gson.toJson(borrowedIndividualBookPostDto);

        // when, then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/stats/borrow/{title}", titleString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("IN_CIRCULATION")));
    }

}
