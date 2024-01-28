package com.example.library.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private int publicationYear;
    private String isbn;
    // other fields, getters, setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
