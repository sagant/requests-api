package uy.requests.api;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.requests.application.RequestService;

@RestController
@RequestMapping("/api/requests")
public class RequestController {
    private final RequestService service;

    public RequestController(RequestService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<RequestDto> create(@Valid @RequestBody CreateRequestDto input) {
        RequestDto result = service.create(input);
        return ResponseEntity.created(URI.create("/api/requests/" + result.id()))
                .body(result);
    }

    @GetMapping
    public List<RequestDto> list() {
        return service.listAll();
    }

    @GetMapping("/summary")
    public RequestSummaryDto summary() {
        return service.summary();
    }

    @GetMapping("/{id}")
    public RequestDto find(@PathVariable Long id) {
        return service.find(id);
    }
}
