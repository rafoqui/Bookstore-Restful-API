package com.restful.bookstore.controller;

import com.restful.bookstore.entities.Author;
import com.restful.bookstore.entities.Book;
import com.restful.bookstore.exceptions.InvalidDataException;
import com.restful.bookstore.exceptions.NotFoundException;
import com.restful.bookstore.entities.AuthorResponse;
import com.restful.bookstore.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        try {
            Author author = authorService.getAuthorById(id);
            return ResponseEntity.ok(author);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Author> getAuthorByName(@PathVariable String name) {
        try {
            Author author = authorService.getAuthorByName(name);
            return ResponseEntity.ok(author);
        } catch (NotFoundException e) {
          return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/booksByAuthor/{id}")
    public ResponseEntity<AuthorResponse> getAuthorWithBooksById(@PathVariable Long id) {
        try {
            Author author = authorService.getAuthorById(id);
            List<Book> books = authorService.getBooksByAuthor(id);
            AuthorResponse response = new AuthorResponse(author, books);
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        try {
             Author newAuthor = authorService.createAuthor(author);
             return ResponseEntity.status(HttpStatus.CREATED).body(newAuthor);
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        try {
            Author updatedAuthor = authorService.updateAuthor(id, author);
            return ResponseEntity.ok(updatedAuthor);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        try {
            boolean deleted = authorService.deleteAuthor(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
