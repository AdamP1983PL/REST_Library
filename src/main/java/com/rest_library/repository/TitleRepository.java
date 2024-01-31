package com.rest_library.repository;

import com.rest_library.entity.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TitleRepository extends JpaRepository<Title, Long> {

    Optional<Title> findByBookTitle(String bookTitle);

    // todo use findByBookTitle lesson 88

}
