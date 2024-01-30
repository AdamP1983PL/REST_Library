package com.rest_library.repository;

import com.rest_library.entity.BooksBorrowingStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksBorrowingStatsRepository extends JpaRepository<BooksBorrowingStats, Long> {
}
