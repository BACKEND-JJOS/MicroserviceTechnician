package co.com.bancolombia.model.service.gateways;

import co.com.bancolombia.model.service.Service;
import reactor.core.publisher.Mono;

public interface ServiceRepository {
    Mono<Service> findById(Integer id);
}
