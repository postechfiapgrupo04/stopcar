package br.com.fiap.stopcar.application.exceptions;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class AppException extends Exception {
    public AppException(String message) {
        super(message);
    }

    public AppException(Collection<String> messages) {
        super(messages.stream().filter(Objects::nonNull).collect(Collectors.joining(" ")));
    }
}
