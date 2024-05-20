package com.webtech.onlineshop.common;

public class RequestException extends RuntimeException {
    public RequestException(String message) {
        super(message);
    }
    public RequestException(Exception ex) {
        super(ex);
    }
}
