package com.example.library.repository;

import com.example.library.entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

// BorrowingRecordRepository.java
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
}
