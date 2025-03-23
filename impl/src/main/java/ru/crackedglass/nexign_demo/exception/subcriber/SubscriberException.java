package ru.crackedglass.nexign_demo.exception.subcriber;

public class SubscriberException extends RuntimeException{
    public SubscriberException(String message) {super(message);}
    public SubscriberException(String message, Exception cause) {super(message, cause);}
}
