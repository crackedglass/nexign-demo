package ru.crackedglass.nexign_demo.exception.cdr;

public class CdrException extends RuntimeException{
    public CdrException(String message) {super(message);}
    public CdrException(String message, Exception cause) {super(message, cause);}
}
