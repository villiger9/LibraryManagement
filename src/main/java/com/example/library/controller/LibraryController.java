package com.example.library.controller;

import io.micrometer.core.instrument.MeterRegistry;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import com.example.library.repository.*;
import com.example.library.entity.*;
import com.example.library.response.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.validation.BindingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.micrometer.core.annotation.Timed;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// LibraryController.java

@RestController
@Validated
@RequestMapping("/api")
public class LibraryController {

    private final Logger logger = LoggerFactory.getLogger(LibraryController.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    private MeterRegistry meterRegistry;

    // Book management endpoints
    @Timed(value = "library.getAllBooks", description = "Time taken to fetch all books")
    @GetMapping("/books")
    public ResponseEntity<?> getAllBooks() {
        try {
            logger.info("Fetching all books");
            List<Book> books = bookRepository.findAll();
            logger.info("Fetched all books successfully");
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            // Log the exception for further investigation
            logger.error("An error occurred while retrieving all books", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request");
        }
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            // If the provided ID is not valid, return a 400 Bad Request with an error message
            String errorMessage = "Invalid book ID provided.";
            logger.error(errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/books")
    public ResponseEntity<?> addBook(@Valid @RequestBody Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // If there are validation errors, log and return a 400 Bad Request with the error message
            StringBuilder errorMessage = new StringBuilder("Validation error(s): ");
            bindingResult.getAllErrors().forEach(error -> {
                if (error instanceof FieldError) {
                    errorMessage.append(((FieldError) error).getField()).append(": ").append(error.getDefaultMessage()).append("; ");
                } else {
                    errorMessage.append(error.getDefaultMessage()).append("; ");
                }
            });

            logger.error(errorMessage.toString());
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        try {
            Book savedBook = bookRepository.save(book);
            return ResponseEntity.status(HttpStatus.CREATED).body("Book added successfully");
        } catch (Exception e) {
            // Log the exception for further investigation
            logger.error("An error occurred while adding a book", e);

            // If an internal server error occurs, return a 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request");
        }
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody @Valid Book updatedBook, BindingResult bindingResult) {
        try {
            Optional<Book> optionalBook = bookRepository.findById(id);

            if (optionalBook.isPresent()) {
                Book existingBook = optionalBook.get();

                // Validate the updatedBook using the provided BindingResult
                if (bindingResult.hasErrors()) {
                    StringBuilder errorMessage = new StringBuilder("Validation error(s): ");
                    bindingResult.getAllErrors().forEach(error -> {
                        if (error instanceof FieldError) {
                            errorMessage.append(((FieldError) error).getField()).append(": ").append(error.getDefaultMessage()).append("; ");
                        } else {
                            errorMessage.append(error.getDefaultMessage()).append("; ");
                        }
                    });
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
                }

                // Perform your additional validation or business logic here before updating
                // ...

                updatedBook.setId(id);  // Set the ID from the path variable
                Book savedBook = bookRepository.save(updatedBook);
                return ResponseEntity.ok(savedBook);
            } else {
                // Book with the specified ID not found
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Log the exception for further investigation
            logger.error("An error occurred while updating the book", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request");
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        try {
            Optional<Book> optionalBook = bookRepository.findById(id);

            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();

                // Perform any additional checks or validations before deleting

                bookRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            } else {
                // Book with the specified ID not found
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Log the exception for further investigation
            logger.error("An error occurred while deleting the book", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Patron management endpoints
    // Implement similar methods for patrons
    @GetMapping("/patrons")
    public ResponseEntity<List<Patron>> getAllPatrons() {
        try {
            // Perform any additional checks or validations before fetching all patrons

            List<Patron> patrons = patronRepository.findAll();
            return ResponseEntity.ok(patrons);
        } catch (Exception e) {
            // Log the exception for further investigation
            logger.error("An error occurred while fetching all patrons", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/patrons/{id}")
    public ResponseEntity<Patron> getPatronById(@PathVariable Long id) {
        try {
            // Perform any additional checks or validations before fetching the patron by ID

            Optional<Patron> optionalPatron = patronRepository.findById(id);
            return optionalPatron.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            // Log the exception for further investigation
            logger.error("An error occurred while fetching a patron by ID", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/patrons")
    public ResponseEntity<?> addPatron(@Valid @RequestBody Patron patron, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                // If there are validation errors, construct a more detailed error message
                StringBuilder errorMessage = new StringBuilder("Validation error(s): ");
                bindingResult.getAllErrors().forEach(error -> {
                    if (error instanceof FieldError) {
                        errorMessage.append(((FieldError) error).getField()).append(": ").append(error.getDefaultMessage()).append("; ");
                    } else {
                        errorMessage.append(error.getDefaultMessage()).append("; ");
                    }
                });
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
            }

            Patron savedPatron = patronRepository.save(patron);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPatron);
        } catch (Exception e) {
            // Log the exception for further investigation
            logger.error("An error occurred while adding a patron", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request");
        }
    }

    @PutMapping("/patrons/{id}")
    public ResponseEntity<?> updatePatron(@PathVariable Long id, @Valid @RequestBody Patron updatedPatron, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                // If there are validation errors, construct a more detailed error message
                StringBuilder errorMessage = new StringBuilder("Validation error(s): ");
                bindingResult.getAllErrors().forEach(error -> {
                    if (error instanceof FieldError) {
                        errorMessage.append(((FieldError) error).getField()).append(": ").append(error.getDefaultMessage()).append("; ");
                    } else {
                        errorMessage.append(error.getDefaultMessage()).append("; ");
                    }
                });
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
            }

            Optional<Patron> optionalPatron = patronRepository.findById(id);
            return optionalPatron.map(patron -> {
                updatedPatron.setId(id);  // Set the ID from the path variable
                Patron savedPatron = patronRepository.save(updatedPatron);
                return ResponseEntity.ok(savedPatron);
            }).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            // Log the exception for further investigation
            logger.error("An error occurred while updating a patron", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request");
        }
    }

    @DeleteMapping("/patrons/{id}")
    public ResponseEntity<?> deletePatron(@PathVariable Long id) {
        try {
            Optional<Patron> optionalPatron = patronRepository.findById(id);
            if (optionalPatron.isPresent()) {
                patronRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Log the exception for further investigation
            logger.error("An error occurred while deleting a patron", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request");
        }
    }

    // Borrowing endpoints
    // Implement methods for borrowing and returning books

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<?> borrowBook(
            @PathVariable Long bookId,
            @PathVariable Long patronId
    ) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Optional<Patron> optionalPatron = patronRepository.findById(patronId);

        if (!optionalBook.isPresent()) {
            // Book not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book with ID " + bookId + " not found.");
        }

        if (!optionalPatron.isPresent()) {
            // Patron not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patron with ID " + patronId + " not found.");
        }

        Book book = optionalBook.get();
        Patron patron = optionalPatron.get();

        // Implement your business logic for borrowing here

        Optional<BorrowingRecord> ongoingBorrowingRecord =
                borrowingRecordRepository.findByBookAndPatronAndReturnDateIsNull(book, patron);

        if (!ongoingBorrowingRecord.isPresent()) {
            // Book is available for borrowing

            BorrowingRecord borrowingRecord = new BorrowingRecord();
            borrowingRecord.setBook(book);
            borrowingRecord.setPatron(patron);
            borrowingRecord.setBorrowDate(LocalDate.now());

            borrowingRecordRepository.save(borrowingRecord);

            return ResponseEntity.status(HttpStatus.CREATED).body(borrowingRecord);
        } else {
            // Book is already borrowed
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book is already borrowed.");
        }
    }

    // Record the return of a borrowed book
    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<String> returnBook(
            @PathVariable Long bookId,
            @PathVariable Long patronId
    ) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Optional<Patron> optionalPatron = patronRepository.findById(patronId);

        if (!optionalBook.isPresent() || !optionalPatron.isPresent()) {
            // Book or Patron not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Book or Patron not found. Book ID: " + bookId + ", Patron ID: " + patronId);
        }

        Book book = optionalBook.get();
        Patron patron = optionalPatron.get();

        // Implement your business logic for returning here

        Optional<BorrowingRecord> optionalBorrowingRecord =
                borrowingRecordRepository.findByBookAndPatronAndReturnDateIsNull(book, patron);

        if (optionalBorrowingRecord.isPresent()) {
            BorrowingRecord borrowingRecord = optionalBorrowingRecord.get();
            borrowingRecord.setReturnDate(LocalDate.now());
            borrowingRecordRepository.save(borrowingRecord);
            return ResponseEntity.ok("Book returned successfully. Borrowing Record ID: " + borrowingRecord.getId());
        } else {
            // Borrowing record not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Borrowing record not found for Book ID: " + bookId + ", Patron ID: " + patronId);
        }
    }
}
