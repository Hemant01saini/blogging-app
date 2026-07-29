package com.blogapp.exception;

public class MediaNotFoundException extends RuntimeException{

    public MediaNotFoundException(String message ) {
        super(message);
    }
}
