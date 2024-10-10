package co.com.bancolombia.r2dbc;

import co.com.bancolombia.r2dbc.entity.ServiceEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MyReactiveRepository extends ReactiveCrudRepository<ServiceEntity, Integer>, ReactiveQueryByExampleExecutor<ServiceEntity> {

}
