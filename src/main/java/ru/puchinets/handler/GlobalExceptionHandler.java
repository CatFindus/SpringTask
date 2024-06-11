package ru.puchinets.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.puchinets.exception.BadRequestException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(UUID.randomUUID(), LocalDateTime.now().toString(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(UUID.randomUUID(), LocalDateTime.now().toString(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorResponse implements Serializable {
        private UUID uuid;
        private String timestamp;
        private String message;
    }
}
