package com.globalbooks.catalog;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bookResponse", propOrder = {
    "book"
})
@XmlRootElement(name = "bookResponse")
public class BookResponse {
    private Book book;
    
    public Book getBook() { 
        return book; 
    }
    
    public void setBook(Book book) { 
        this.book = book; 
    }
}
