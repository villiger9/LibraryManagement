package com.example.library.repository;

import com.example.library.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

// PatronRepository.java
public interface PatronRepository extends JpaRepository<Patron, Long> {
}
