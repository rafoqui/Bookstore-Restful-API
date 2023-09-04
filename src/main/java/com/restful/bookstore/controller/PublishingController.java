package com.restful.bookstore.controller;

import com.restful.bookstore.entities.Book;
import com.restful.bookstore.entities.Publishing;
import com.restful.bookstore.exceptions.InvalidDataException;
import com.restful.bookstore.exceptions.NotFoundException;
import com.restful.bookstore.entities.PublishingResponse;
import com.restful.bookstore.services.PublishingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishing")
public class PublishingController {

    private final PublishingService publishingService;

    @Autowired
    public PublishingController(PublishingService publishingService) {
        this.publishingService = publishingService;
    }

    @GetMapping
    public List<Publishing> getAllPublishers() {
        return publishingService.getAllPublishing();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Publishing> getPublishingById(@PathVariable Long id) {
        try {
            Publishing publishing = publishingService.getPublishingById(id);
            return ResponseEntity.ok(publishing);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Publishing> getPublishingByName(@PathVariable String name) {
        try {
            Publishing publishing = publishingService.getPublishingByName(name);
            return ResponseEntity.ok(publishing);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/booksByPublishing/{id}")
    public ResponseEntity<PublishingResponse> getPublishingWithBooksById(@PathVariable Long id) {
        try {
            Publishing publishing = publishingService.getPublishingById(id);
            List<Book> books = publishingService.getBooksByPublishing(id);
            PublishingResponse response = new PublishingResponse(publishing, books);
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Publishing> createPublishing(@RequestBody Publishing publishing) {
        try {
            Publishing newPublishing = publishingService.createPublishing(publishing);
            return ResponseEntity.status(HttpStatus.CREATED).body(newPublishing);
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publishing> updatePublishing(@PathVariable Long id, @RequestBody Publishing publishing) {
        try {
            Publishing updatedPublishing = publishingService.updatePublishing(id, publishing);
            return ResponseEntity.ok(updatedPublishing);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublishing(@PathVariable Long id) {
        try {
            boolean deleted = publishingService.deletePublishing(id);
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
