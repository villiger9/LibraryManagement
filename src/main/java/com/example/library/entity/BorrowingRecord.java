package com.example.library.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

// BorrowingRecord.java
@Entity
public class BorrowingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @ManyToOne
    @JoinColumn(name = "patron_id")
    private Patron patron;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    // other fields, getters, setters
}