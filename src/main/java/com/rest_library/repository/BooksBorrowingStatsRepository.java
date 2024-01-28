package com.rest_library.repository;

import com.rest_library.entity.BooksBorrowingStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksBorrowingStatsRepository extends JpaRepository<BooksBorrowingStats, Long> {
}
