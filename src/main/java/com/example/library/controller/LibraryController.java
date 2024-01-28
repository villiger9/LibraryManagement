package com.example.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.library.repository.*;
import com.example.library.entity.*;

import java.util.List;
import java.util.Optional;

// LibraryController.java
@RestController
@RequestMapping("/api")
public class LibraryController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    // Book management endpoints
    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book savedBook = bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.map(book -> {
            updatedBook.setId(id);  // Set the ID from the path variable
            Book savedBook = bookRepository.save(updatedBook);
            return ResponseEntity.ok(savedBook);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            bookRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Patron management endpoints
    // Implement similar methods for patrons
    @GetMapping("/patrons")
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    @GetMapping("/patrons/{id}")
    public ResponseEntity<Patron> getPatronById(@PathVariable Long id) {
        Optional<Patron> optionalPatron = patronRepository.findById(id);
        return optionalPatron.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/patrons")
    public ResponseEntity<Patron> addPatron(@RequestBody Patron patron) {
        Patron savedPatron = patronRepository.save(patron);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPatron);
    }

    @PutMapping("/patrons/{id}")
    public ResponseEntity<Patron> updatePatron(@PathVariable Long id, @RequestBody Patron updatedPatron) {
        Optional<Patron> optionalPatron = patronRepository.findById(id);
        return optionalPatron.map(patron -> {
            updatedPatron.setId(id);  // Set the ID from the path variable
            Patron savedPatron = patronRepository.save(updatedPatron);
            return ResponseEntity.ok(savedPatron);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/patrons/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable Long id) {
        Optional<Patron> optionalPatron = patronRepository.findById(id);
        if (optionalPatron.isPresent()) {
            patronRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Borrowing endpoints
    // Implement methods for borrowing and returning books

}
