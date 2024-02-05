package com.rest_library.repository;

import com.rest_library.entity.IndividualBook;
import com.rest_library.entity.Title;
import com.rest_library.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IndividualBookRepository extends JpaRepository<IndividualBook, Long> {

    Optional<IndividualBook> findByTitleBookTitleAndStatus(String title_bookTitle, Status status);

}
