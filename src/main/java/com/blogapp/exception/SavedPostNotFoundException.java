package com.blogapp.exception;

public class SavedPostNotFoundException extends RuntimeException{

    public SavedPostNotFoundException(String message){
        super(message);
    }
}
