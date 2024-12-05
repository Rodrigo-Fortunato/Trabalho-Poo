package edu.curso.controller;

public class EventosException extends Exception{
    public EventosException() {
    }

    public EventosException(String message) {
        super(message);
    }

    public EventosException(Throwable cause) {
        super(cause);
    }
}
