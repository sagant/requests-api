package uy.requests.application;

import java.util.List;
import java.util.Map;
import java.util.EnumMap;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uy.requests.api.CreateRequestDto;
import uy.requests.api.RequestDto;
import uy.requests.api.RequestSummaryDto;
import uy.requests.domain.RequestType;
import uy.requests.domain.RequestEntity;
import uy.requests.persistence.RequestRepository;

@Service
public class RequestService {
    private final RequestRepository repository;

    public RequestService(RequestRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public RequestDto create(CreateRequestDto input) {
        RequestEntity entity = new RequestEntity(
                input.title(), input.description(), input.type()
        );
        return RequestDto.fromEntity(repository.saveAndFlush(entity));
    }

    @Transactional(readOnly = true)
    public List<RequestDto> listAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream().map(RequestDto::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public RequestSummaryDto summary() {
        Map<RequestType, Long> byType = new EnumMap<>(RequestType.class);
        for (RequestType type : RequestType.values()) {
            byType.put(type, 0L);
        }
        for (RequestEntity request : repository.findAll()) {
            byType.merge(request.getType(), 1L, Long::sum);
        }
        long total = byType.values().stream().mapToLong(Long::longValue).sum();
        return new RequestSummaryDto(total, byType);
    }

    @Transactional(readOnly = true)
    public RequestDto find(Long id) {
        return repository.findById(id)
                .map(RequestDto::fromEntity)
                .orElseThrow(() -> new RequestNotFoundException(id));
    }
}
