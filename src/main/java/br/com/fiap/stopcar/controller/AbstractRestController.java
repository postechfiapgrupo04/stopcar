package br.com.fiap.stopcar.controller;

import br.com.fiap.stopcar.application.dto.exceptions.ResponseErrorDTO;
import br.com.fiap.stopcar.application.exceptions.AppException;
import jakarta.persistence.MappedSuperclass;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
public abstract class AbstractRestController {
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ResponseErrorDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ResponseErrorDTO> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            errors.add(new ResponseErrorDTO(HttpStatus.PRECONDITION_FAILED.value(), error.getDefaultMessage()));
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(AppException.class)
    public ResponseErrorDTO handleValidationExceptions(AppException ex) {
        return new ResponseErrorDTO(HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());
    }


}
