package com.rest_library.repository;

import com.rest_library.entity.IndividualBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualBookRepository extends JpaRepository<IndividualBook, Long> {
}
