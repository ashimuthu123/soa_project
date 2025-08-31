package com.globalbooks.orders.exception;

import java.util.List;

public class OrderValidationException extends RuntimeException {
    private List<String> errors;

    public OrderValidationException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
