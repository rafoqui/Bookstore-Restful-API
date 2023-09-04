package com.restful.bookstore.repositories;

import com.restful.bookstore.entities.Author;
import com.restful.bookstore.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByName(String name);

}
