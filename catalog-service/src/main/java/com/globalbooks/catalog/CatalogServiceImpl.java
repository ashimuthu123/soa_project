package com.globalbooks.catalog;

import jakarta.jws.WebService;
import jakarta.xml.ws.Endpoint;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebService(
    targetNamespace = "http://catalog.globalbooks.com/",
    portName = "CatalogServicePort",
    serviceName = "CatalogService",
    endpointInterface = "com.globalbooks.catalog.CatalogServicePortType"
)
public class CatalogServiceImpl implements CatalogServicePortType {
    
    private final List<Book> bookDatabase;
    
    public CatalogServiceImpl() {
        bookDatabase = new ArrayList<>();
        initializeSampleData();
    }
    
    private void initializeSampleData() {
        Book book1 = new Book();
        book1.setId("BOOK-001");
        book1.setIsbn("978-0-123456-47-2");
        book1.setTitle("The Great Gatsby");
        book1.setAuthor("F. Scott Fitzgerald");
        book1.setCategory("Fiction");
        book1.setPrice(new BigDecimal("12.99"));
        book1.setStockLevel(50);
        book1.setDescription("A story of the fabulously wealthy Jay Gatsby and his love for the beautiful Daisy Buchanan.");
        bookDatabase.add(book1);
        
        Book book2 = new Book();
        book2.setId("BOOK-002");
        book2.setIsbn("978-0-123456-48-9");
        book2.setTitle("To Kill a Mockingbird");
        book2.setAuthor("Harper Lee");
        book2.setCategory("Fiction");
        book2.setPrice(new BigDecimal("14.99"));
        book2.setStockLevel(75);
        book2.setDescription("The story of young Scout Finch and her father Atticus in a racially divided Alabama town.");
        bookDatabase.add(book2);
        
        Book book3 = new Book();
        book3.setId("BOOK-003");
        book3.setIsbn("978-0-123456-49-6");
        book3.setTitle("1984");
        book3.setAuthor("George Orwell");
        book3.setCategory("Science Fiction");
        book3.setPrice(new BigDecimal("11.99"));
        book3.setStockLevel(30);
        book3.setDescription("A dystopian novel about totalitarianism and surveillance society.");
        bookDatabase.add(book3);
    }
    
    public BookResponse getBook(GetBookRequest request) {
        BookResponse response = new BookResponse();
        
        // Find book by ISBN
        Book foundBook = bookDatabase.stream()
            .filter(book -> book.getIsbn().equals(request.getIsbn()))
            .findFirst()
            .orElse(null);
        
        if (foundBook != null) {
            response.setBook(foundBook);
        } else {
            // Return a default book if not found
            Book defaultBook = new Book();
            defaultBook.setId("NOT-FOUND");
            defaultBook.setIsbn(request.getIsbn());
            defaultBook.setTitle("Book Not Found");
            defaultBook.setAuthor("Unknown");
            defaultBook.setCategory("Unknown");
            defaultBook.setPrice(new BigDecimal("0.00"));
            defaultBook.setStockLevel(0);
            defaultBook.setDescription("The requested book was not found in the catalog.");
            response.setBook(defaultBook);
        }
        
        return response;
    }
    
    public BookSearchResponse searchBooks(SearchBooksRequest request) {
        BookSearchResponse response = new BookSearchResponse();
        List<Book> matchingBooks = new ArrayList<>();
        
        for (Book book : bookDatabase) {
            boolean matches = true;
            
            if (request.getTitle() != null && !request.getTitle().isEmpty()) {
                matches = matches && book.getTitle().toLowerCase().contains(request.getTitle().toLowerCase());
            }
            
            if (request.getAuthor() != null && !request.getAuthor().isEmpty()) {
                matches = matches && book.getAuthor().toLowerCase().contains(request.getAuthor().toLowerCase());
            }
            
            if (request.getCategory() != null && !request.getCategory().isEmpty()) {
                matches = matches && book.getCategory().equalsIgnoreCase(request.getCategory());
            }
            
            if (request.getMaxPrice() != null) {
                matches = matches && book.getPrice().compareTo(request.getMaxPrice()) <= 0;
            }
            
            if (matches) {
                matchingBooks.add(book);
            }
        }
        
        BookArray bookArray = new BookArray();
        bookArray.setBook(matchingBooks);
        response.setBooks(bookArray);
        
        return response;
    }

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8080/catalog-service/CatalogService", new CatalogServiceImpl());
        System.out.println("CatalogService started at http://localhost:8080/catalog-service/CatalogService");
    }
}