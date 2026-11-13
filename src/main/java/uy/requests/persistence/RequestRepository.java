package uy.requests.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import uy.requests.domain.RequestEntity;

public interface RequestRepository extends JpaRepository<RequestEntity, Long> {
}
