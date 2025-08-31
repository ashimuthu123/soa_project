package com.globalbooks.catalog;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;

@WebService(targetNamespace = "http://catalog.globalbooks.com/")
public interface CatalogServicePortType {
    @WebMethod
    BookResponse getBook(@WebParam(name = "parameters") GetBookRequest parameters);
    
    @WebMethod
    BookSearchResponse searchBooks(@WebParam(name = "parameters") SearchBooksRequest parameters);
}
