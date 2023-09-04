package com.restful.bookstore.services;

import com.restful.bookstore.entities.Author;
import com.restful.bookstore.entities.Book;
import com.restful.bookstore.entities.Publishing;
import com.restful.bookstore.exceptions.InvalidDataException;
import com.restful.bookstore.exceptions.NotFoundException;
import com.restful.bookstore.repositories.AuthorRepository;
import com.restful.bookstore.repositories.BookRepository;
import com.restful.bookstore.repositories.PublishingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository repository;
    private final AuthorRepository authorRepository;
    private final PublishingRepository publishingRepository;
    private final AuthorService authorService;
    private final PublishingService publishingService;

    @Autowired
    public BookService(BookRepository repository, AuthorRepository authorRepository, PublishingRepository publishingRepository, AuthorService authorService, PublishingService publishingService) {
        this.repository = repository;
        this.authorRepository = authorRepository;
        this.publishingRepository = publishingRepository;
        this.authorService = authorService;
        this.publishingService = publishingService;
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public Book getBookByIsbn(String isbn) {
        return repository.findById(isbn)
                .orElseThrow(() -> new NotFoundException("Book not found"));
    }

    public Book getBookByTitle(String title) {
        return repository.findByTitle(title)
                .orElseThrow(() -> new NotFoundException("Book not found"));
    }

    public Book getBookByGenre(String genre) {
        return repository.findByGenre(genre).orElseThrow(() -> new NotFoundException("Book not found"));
    }

    public Book getBookByYear(Integer year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);

        return repository.findByDateOfPublicationBetween(startDate, endDate).orElseThrow(() -> new NotFoundException("Book not found"));
    }

    @Transactional
    public Book createBook(Book book) {
        validation(book);

        Optional<Author> existingAuthor = authorRepository.findByName(book.getAuthor().getName());
        if (existingAuthor.isPresent()) {
            book.setAuthor(existingAuthor.get());
        } else {
            Author newAuthor = book.getAuthor();
            authorService.createAuthor(newAuthor);
            book.setAuthor(newAuthor);
        }

        Optional<Publishing> existingPublishing = publishingRepository.findByName(book.getPublishing().getName());
        if (existingPublishing.isPresent()) {
            book.setPublishing(existingPublishing.get());
        } else {
            Publishing newPublishing = book.getPublishing();
            publishingService.createPublishing(newPublishing);
            book.setPublishing(newPublishing);
        }

        return repository.save(book);
    }


    @Transactional
    public Book updateBook(String isbn, Book newBook) {
        return repository.findById(isbn)
                .map(oldBook -> {
                    newBook.setIsbn(isbn);
                    validation(newBook);
                    return repository.save(newBook);
                })
                .orElseThrow(() -> new NotFoundException("Book not found"));
    }

    @Transactional
    public boolean deleteBook(String isbn) {
        Optional<Book> bookOptional = repository.findById(isbn);
        if (bookOptional.isPresent()) {
            Book oldBook = bookOptional.get();
            repository.delete(oldBook);
            return true;
        } else {
            throw new NotFoundException("Book not found");
        }
    }

    public void validation(Book book) {
        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            throw new InvalidDataException("Title required");
        }
        if (book.getAuthor() == null) {
            throw new InvalidDataException("Author required");
        }
        if (book.getPublishing() == null) {
            throw new InvalidDataException("Publishing required");
        }
        if (book.getIsbn() == null || book.getIsbn().isEmpty()) {
            throw new InvalidDataException("ISBN required");
        }
        if (book.getDateOfPublication() == null) {
            throw new InvalidDataException("Date of publication required");
        }
    }
}
