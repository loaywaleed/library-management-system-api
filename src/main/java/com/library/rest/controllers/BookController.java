package com.library.rest.controllers;

import com.library.rest.exceptions.ConflictException;
import com.library.rest.exceptions.ResourceNotFoundException;
import com.library.rest.models.Book;
import com.library.rest.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookRepo bookRepo;


    @GetMapping(value = "/books")
    public List<Book> getBooks() {
        return bookRepo.findAll();
    }

    @PostMapping(value = "/books")
    public ResponseEntity<Book> createPatron(@RequestBody Book book) {
        if (bookRepo.findByIsbn(book.getIsbn()).isPresent()) {
            throw new ConflictException("A book with the ISBN " + book.getIsbn() + " already exists.");
        }
        Book savedBook = bookRepo.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
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
