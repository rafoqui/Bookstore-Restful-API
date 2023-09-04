package com.restful.bookstore.entities;

import java.util.List;

public class AuthorResponse {
    private Author author;
    private List<Book> books;

    public AuthorResponse(Author author, List<Book> books) {
        this.author = author;
        this.books = books;
    }

    public Author getAuthor() {
        return author;
    }

    public List<Book> getBooks() {
        return books;
    }
}

