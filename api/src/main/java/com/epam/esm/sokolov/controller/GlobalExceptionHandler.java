package com.epam.esm.sokolov.controller;

import com.epam.esm.sokolov.model.Tag;
import com.epam.esm.sokolov.repository.RepositoryException;
import com.epam.esm.sokolov.repository.tag.TagRepository;
import com.epam.esm.sokolov.repository.tag.TagRepositoryImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
class GlobalExceptionHandler {

    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_CODE = "errorCode";

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> defaultErrorHandler(Exception e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ERROR_MESSAGE, e.getMessage());
        errorMap.put(ERROR_CODE, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        return errorMap;
    }

    @ExceptionHandler(RepositoryException.class)
    public ResponseEntity handleRepositoryException(RepositoryException e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ERROR_MESSAGE, e.getMessage());

        if (e.getClazz() == TagRepositoryImpl.class) {
            errorMap.put(ERROR_CODE, e.getStatusCode().value() + "01");
        } else {
            errorMap.put(ERROR_CODE, e.getStatusCode().value() + "02");
        }

        return ResponseEntity
                .status(e.getStatusCode())
                .body(errorMap);
    }
}
