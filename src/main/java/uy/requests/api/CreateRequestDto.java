package uy.requests.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import uy.requests.domain.RequestType;

public record CreateRequestDto(
        @NotBlank @Size(min = 3, max = 120) String title,
        @NotBlank @Size(min = 10, max = 500) String description,
        @NotNull RequestType type
) {
}
