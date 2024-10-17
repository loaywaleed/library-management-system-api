package com.library.rest.controllers;

import com.library.rest.models.Book;
import com.library.rest.Repo.BookRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(controllers = ApiControllers.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepo bookRepo;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Author");
        book.setIsbn("1234567890");
        book.setPublicationYear(2023);
    }

    /* GET books */
    // Test GET /books
    @Test
    public void shouldReturnAllBooks() throws Exception {
        when(bookRepo.findAll()).thenReturn(Arrays.asList(book));

        mockMvc.perform(get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(book.getTitle()))
                .andExpect(jsonPath("$[0].author").value(book.getAuthor()));
    }


    // Test GET /books/{id}
    @Test
    public void shouldReturnBookById() throws Exception {
        when(bookRepo.findById(anyLong())).thenReturn(Optional.of(book));

        mockMvc.perform(get("/api/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()));
    }

    // Test GET /books/{id} - Book Not Found
    @Test
    public void shouldReturn404WhenBookNotFound() throws Exception {
        when(bookRepo.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    /* POST books */
    // Test POST /books
    @Test
    public void shouldCreateNewBook() throws Exception {
        when(bookRepo.save(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"New Book\", \"author\": \"New Author\", \"isbn\": \"123456789\", \"publicationYear\": 2023}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("Author"));
    }


    /* PUT books */
    // PUT /books/{id}
    @Test
    public void shouldUpdateBookById() throws Exception {
        when(bookRepo.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookRepo.save(any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/api/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Title\", \"author\": \"Updated Author\", \"isbn\": \"123456789\", \"publicationYear\": 2022}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.author").value("Updated Author"));
    }


    /* DELETE books */
    // Test DELETE /books/{id}
    @Test
    public void shouldDeleteBookById() throws Exception {
        when(bookRepo.findById(anyLong())).thenReturn(Optional.of(book));
        doNothing().when(bookRepo).delete(any(Book.class));

        mockMvc.perform(delete("/api/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    // Test DELETE /books/{id} - Book Not Found
    @Test
    public void shouldReturn404WhenDeletingNonExistingBook() throws Exception {
        when(bookRepo.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}