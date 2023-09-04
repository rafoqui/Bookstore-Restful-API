package com.restful.bookstore.controller;

import com.restful.bookstore.entities.Book;
import com.restful.bookstore.exceptions.InvalidDataException;
import com.restful.bookstore.exceptions.NotFoundException;
import com.restful.bookstore.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        try {
            Book book = bookService.getBookByIsbn(isbn);
            return ResponseEntity.ok(book);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Book> getBookByTitle(@PathVariable String title) {
        try {
            Book book = bookService.getBookByTitle(title);
            return ResponseEntity.ok(book);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<Book> getBookByGenre(@PathVariable String genre) {
        try {
            Book book = bookService.getBookByGenre(genre);
            return ResponseEntity.ok(book);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/yearOfPublication/{year}")
    public ResponseEntity<Book> getBookByYear(@PathVariable Integer year) {
        try {
            Book book = bookService.getBookByYear(year);
            return ResponseEntity.ok(book);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        try {
            Book newBook = bookService.createBook(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(newBook);
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<Book> updateBook(@PathVariable String isbn, @RequestBody Book book) {
        try {
            Book updatedBook = bookService.updateBook(isbn, book);
            return ResponseEntity.ok(updatedBook);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> deleteBook(@PathVariable String isbn) {
        try {
            boolean deleted = bookService.deleteBook(isbn);
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
