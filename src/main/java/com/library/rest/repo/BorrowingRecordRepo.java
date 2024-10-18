package com.library.rest.repo;

import com.library.rest.models.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRecordRepo extends JpaRepository<BorrowingRecord, Long> {
}