package com.library.rest.Controllers;
import com.library.rest.Models.Book;
import com.library.rest.Repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
    public Book addBook(@RequestBody Book book) {
        return bookRepo.save(book);
    }

    @GetMapping(value = "/books/{id}")
    public Book getBookById(@PathVariable long id) {
        return bookRepo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found with id: " + id));
    }

    @DeleteMapping(value = "/books/{id}")
    public String deleteBookById(@PathVariable long id) {
        Book bookToDelete = bookRepo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found with id: " + id));
        bookRepo.delete(bookToDelete);
        return "book deleted";
    }

    @PutMapping(value = "/books/{id}")
    public Book updateBookById(@PathVariable long id, @RequestBody Book book) {
        Book bookToUpdate = bookRepo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found with id: " + id));
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setIsbn(book.getIsbn());
        bookToUpdate.setPublicationYear(book.getPublicationYear());
        return bookRepo.save(bookToUpdate);
    }
}
