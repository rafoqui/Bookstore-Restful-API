package com.restful.bookstore.services;

import com.restful.bookstore.entities.Book;
import com.restful.bookstore.entities.Publishing;
import com.restful.bookstore.exceptions.InvalidDataException;
import com.restful.bookstore.exceptions.NotFoundException;
import com.restful.bookstore.repositories.PublishingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PublishingService {

    private final PublishingRepository repository;

    @Autowired
    public PublishingService(PublishingRepository repository) {
        this.repository = repository;
    }

    public List<Publishing> getAllPublishing() {
        return repository.findAll();
    }

    public Publishing getPublishingById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Publishing not found"));
    }

    public Publishing getPublishingByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Publishing not found"));
    }

    public List<Book> getBooksByPublishing(Long id) {
        Optional<Publishing> publishingOptional = repository.findById(id);
        if (publishingOptional.isPresent()) {
            Publishing publishing = publishingOptional.get();
            return publishing.getBooks();
        } else {
            throw new NotFoundException("Publishing not found");
        }
    }

    @Transactional
    public Publishing createPublishing(Publishing publishing) {
        validation(publishing);
            return repository.save(publishing);
    }


    @Transactional
    public Publishing updatePublishing(Long id, Publishing newPublishing) {
        return repository.findById(id)
                .map(oldPublishing -> {
                    newPublishing.setId(id);
                    validation(newPublishing);
                    return repository.save(newPublishing);
                })
                .orElseThrow(() -> new NotFoundException("Publishing not found"));
    }

    @Transactional
    public boolean deletePublishing(Long id) {
        Optional<Publishing> publishingOptional = repository.findById(id);
        if (publishingOptional.isPresent()) {
            Publishing oldPublishing = publishingOptional.get();
            repository.delete(oldPublishing);
            return true;
        } else {
            throw new NotFoundException("Publishing not found");
        }
    }

    public void validation(Publishing publishing) {
        if (publishing.getName() == null || publishing.getName().isEmpty()) {
            throw new InvalidDataException("Publishing name required");
        }
    }
}
