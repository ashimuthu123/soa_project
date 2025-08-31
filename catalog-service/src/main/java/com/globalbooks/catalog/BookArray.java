package com.globalbooks.catalog;

import java.util.List;

public class BookArray {
    private List<Book> book;

    // Default constructor
    public BookArray() {}

    // Getters and Setters
    public List<Book> getBook() {
        return book;
    }

    public void setBook(List<Book> book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "BookArray{" +
                "book=" + book +
                '}';
    }
}
