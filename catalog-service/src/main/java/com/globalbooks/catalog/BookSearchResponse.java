package com.globalbooks.catalog;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bookSearchResponse", propOrder = {
    "books"
})
@XmlRootElement(name = "bookSearchResponse")
public class BookSearchResponse {
    private BookArray books;

    // Default constructor
    public BookSearchResponse() {}

    // Getters and Setters
    public BookArray getBooks() {
        return books;
    }

    public void setBooks(BookArray books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "BookSearchResponse{" +
                "books=" + books +
                '}';
    }
}
