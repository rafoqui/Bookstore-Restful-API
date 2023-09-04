package com.restful.bookstore.services;

import com.restful.bookstore.entities.Author;
import com.restful.bookstore.entities.Book;
import com.restful.bookstore.exceptions.InvalidDataException;
import com.restful.bookstore.exceptions.NotFoundException;
import com.restful.bookstore.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository repository;

    @Autowired
    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public List<Author> getAllAuthors() {
        return repository.findAll();
    }

    public Author getAuthorById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author not found"));
    }

    public Author getAuthorByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Author not found"));
    }

    public List<Book> getBooksByAuthor(Long id) {
        Optional<Author> authorOptional = repository.findById(id);
        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            return author.getBooks();
        } else {
            throw new NotFoundException("Author not found");
        }
    }

    @Transactional
    public Author createAuthor(Author author) {
        validation(author);
         return repository.save(author);
    }

    @Transactional
    public Author updateAuthor(Long id, Author newAuthor) {
        return repository.findById(id)
                .map(oldAuthor -> {
                    newAuthor.setId(id);
                    validation(newAuthor);
                    return repository.save(newAuthor);
                })
                .orElseThrow(() -> new NotFoundException("Author not found"));
    }

    @Transactional
    public boolean deleteAuthor(Long id) {
        Optional<Author> authorOptional = repository.findById(id);
        if (authorOptional.isPresent()) {
            Author oldAuthor = authorOptional.get();
            repository.delete(oldAuthor);
            return true;
        } else {
            throw new NotFoundException("Author not found");
        }
    }

    public void validation(Author author) {
        if (author.getName() == null || author.getName().isEmpty()) {
            throw new InvalidDataException("Author name required");
        }
    }
}
