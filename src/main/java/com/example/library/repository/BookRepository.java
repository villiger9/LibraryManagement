package com.example.library.repository;
import com.example.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

// BookRepository.java
public interface BookRepository extends JpaRepository<Book, Long> {
}
