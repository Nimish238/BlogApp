package com.example.Nimish.BlogApp.exceptions;

public class apiException extends RuntimeException{

    public apiException(String message){
        super(message );
    }

    public apiException(){
        super();
    }

}
