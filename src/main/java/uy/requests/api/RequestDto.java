package uy.requests.api;

import java.time.Instant;
import uy.requests.domain.RequestEntity;
import uy.requests.domain.RequestType;

public record RequestDto(Long id, String title, String description,
                         RequestType type, Instant createdAt) {
    public static RequestDto fromEntity(RequestEntity request) {
        return new RequestDto(
                request.getId(),
                request.getTitle(),
                request.getDescription(),
                request.getType(),
                request.getCreatedAt()
        );
    }
}
