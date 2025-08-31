package com.globalbooks.catalog;

import java.math.BigDecimal;

public class SearchBooksRequest {
    private String title;
    private String author;
    private String category;
    private BigDecimal maxPrice;

    // Default constructor
    public SearchBooksRequest() {}

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Override
    public String toString() {
        return "SearchBooksRequest{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", maxPrice=" + maxPrice +
                '}';
    }
}
