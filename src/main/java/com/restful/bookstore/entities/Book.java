package com.restful.bookstore.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@ToString
public class Book {

    @Id
    @Column(unique = true, nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column(name = "date_of_publication", nullable = false)
    private LocalDate dateOfPublication;

    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "genre")
    private List<String> genre = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author", nullable = false)
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publishing", nullable = false)
    private Publishing publishing;

    public void addGenre(String genre) {
        this.genre.add(genre);
    }

    public Book() {
    }

    public Book(String isbn, String title, LocalDate dateOfPublication, List<String> genre, Author author, Publishing publishing) {
        this.isbn = isbn;
        this.title = title;
        this.dateOfPublication = dateOfPublication;
        this.genre = genre;
        this.author = author;
        this.publishing = publishing;
    }

}
