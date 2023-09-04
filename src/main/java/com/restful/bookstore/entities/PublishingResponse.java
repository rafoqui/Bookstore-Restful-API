package com.restful.bookstore.entities;

import java.util.List;

public class PublishingResponse {
    private Publishing publishing;
    private List<Book> books;

    public PublishingResponse(Publishing publishing, List<Book> books) {
        this.publishing = publishing;
        this.books = books;
    }

    public Publishing getPublishing() {
        return publishing;
    }

    public List<Book> getBooks() {
        return books;
    }
}
