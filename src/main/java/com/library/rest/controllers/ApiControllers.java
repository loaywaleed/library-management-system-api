package com.library.rest.controllers;

import com.library.rest.Exceptions.ResourceNotFoundException;
import com.library.rest.models.Book;
import com.library.rest.Repo.BookRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api")
public class ApiControllers {

    @Autowired
    private BookRepo bookRepo;


    @GetMapping(value = "/books")
    public List<Book> getBooks() {
        return bookRepo.findAll();
    }

    @PostMapping(value = "/books")
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@Valid @RequestBody Book book) {
        return bookRepo.save(book);
    }

    @GetMapping(value ="/books/{id}")
    public Book getBookById(@PathVariable long id) {
        return bookRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
    }

    @DeleteMapping(value ="/books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookById(@PathVariable long id) {
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
        bookRepo.delete(book);
    }

    @PutMapping(value = "/books/{id}")
    public Book updateBookById(@PathVariable long id, @RequestBody Book book) {
        Book bookToUpdate = bookRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setIsbn(book.getIsbn());
        bookToUpdate.setPublicationYear(book.getPublicationYear());
        return bookRepo.save(bookToUpdate);
    }
}
