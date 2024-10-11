package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.service.Service;
import co.com.bancolombia.r2dbc.entity.ServiceEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface ServiceReactiveRepository extends ReactiveCrudRepository<ServiceEntity, Integer>, ReactiveQueryByExampleExecutor<ServiceEntity> {
    Flux<ServiceEntity> findAllBy(Pageable pageable);

    Flux<ServiceEntity> findAllByTechnicianIdAndStartDateGreaterThanEqualAndEndDateLessThanEqual(int technicianId, LocalDateTime startDate, LocalDateTime endDate);
}
