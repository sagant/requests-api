package uy.requests.application;

public class RequestNotFoundException extends RuntimeException {
    public RequestNotFoundException(Long id) {
        super("Solicitud " + id + " inexistente");
    }
}
