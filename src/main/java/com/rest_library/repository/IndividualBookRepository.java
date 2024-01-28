package com.rest_library.repository;

import com.rest_library.entity.IndividualBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndividualBookRepository extends JpaRepository<IndividualBook, Long> {
}
