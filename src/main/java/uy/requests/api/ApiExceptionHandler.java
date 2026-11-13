package uy.requests.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uy.requests.application.RequestNotFoundException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RequestNotFoundException.class)
    public ResponseEntity<ProblemDetail> notFound(RequestNotFoundException ex) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, ex.getMessage());
        detail.setTitle("Solicitud inexistente");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> invalid(MethodArgumentNotValidException ex) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Datos de solicitud invalidos");
        detail.setTitle("Validacion fallida");
        return ResponseEntity.badRequest().body(detail);
    }
}
