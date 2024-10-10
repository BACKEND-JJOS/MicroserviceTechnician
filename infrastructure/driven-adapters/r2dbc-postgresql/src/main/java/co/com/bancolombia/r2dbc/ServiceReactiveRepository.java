package co.com.bancolombia.r2dbc;

import co.com.bancolombia.r2dbc.entity.ServiceEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ServiceReactiveRepository extends ReactiveCrudRepository<ServiceEntity, Integer>, ReactiveQueryByExampleExecutor<ServiceEntity> {
    Flux<ServiceEntity> findAllBy(Pageable pageable);
}
