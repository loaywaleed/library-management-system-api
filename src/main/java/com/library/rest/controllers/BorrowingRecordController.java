package com.library.rest.controllers;

import java.util.Date;

import com.library.rest.exceptions.ResourceNotFoundException;
import com.library.rest.models.Book;
import com.library.rest.models.BorrowingRecord;
import com.library.rest.models.Patron;
import com.library.rest.repo.BookRepo;
import com.library.rest.repo.BorrowingRecordRepo;
import com.library.rest.repo.PatronRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class BorrowingRecordController {

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private PatronRepo patronRepo;

    @Autowired
    private BorrowingRecordRepo borrowingRecordRepo;

    // Create a new borrowing record
    @PostMapping("/borrow/{bookId}/patrons/{patronId}")
    public ResponseEntity<BorrowingRecord> borrowBook(
            @PathVariable Long bookId,
            @PathVariable Long patronId) {

        Book book = bookRepo.findById(bookId).orElseThrow(() ->
                new ResourceNotFoundException("Book", "ID", bookId));
        Patron patron = patronRepo.findById(patronId).orElseThrow(() ->
                new ResourceNotFoundException("Patron", "ID", patronId));

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        Date currentDate = new Date();
        // Print the current date
        System.out.println("Current Date: " + currentDate);
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowDate(currentDate);
        borrowingRecord.setStatus("Borrowed");
        BorrowingRecord savedRecord = borrowingRecordRepo.save(borrowingRecord);

        return ResponseEntity.status(201).body(savedRecord);
    }
}
