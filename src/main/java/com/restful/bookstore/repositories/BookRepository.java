package com.restful.bookstore.repositories;


import com.restful.bookstore.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    Optional<Book> findByTitle(String title);

    Optional<Book> findByGenre(String genre);

    Optional<Book> findByDateOfPublicationBetween(LocalDate startDate, LocalDate endDate);

}
