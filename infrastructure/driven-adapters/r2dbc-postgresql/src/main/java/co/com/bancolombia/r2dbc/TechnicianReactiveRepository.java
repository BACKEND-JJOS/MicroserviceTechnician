package co.com.bancolombia.r2dbc;

import co.com.bancolombia.r2dbc.entity.TechnicianEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TechnicianReactiveRepository extends ReactiveCrudRepository<TechnicianEntity, Integer>, ReactiveQueryByExampleExecutor<TechnicianEntity> {

}
