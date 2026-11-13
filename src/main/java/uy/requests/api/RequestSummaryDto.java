package uy.requests.api;

import java.util.Map;
import uy.requests.domain.RequestType;

public record RequestSummaryDto(long total, Map<RequestType, Long> byType) {
}
