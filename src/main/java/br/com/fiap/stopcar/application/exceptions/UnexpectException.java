package br.com.fiap.stopcar.application.exceptions;

public class UnexpectException extends Exception {
    public UnexpectException(Exception e) {
        super(e);
    }
}
