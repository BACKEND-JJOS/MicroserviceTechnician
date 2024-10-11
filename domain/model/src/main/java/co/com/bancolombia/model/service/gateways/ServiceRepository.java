package co.com.bancolombia.model.service.gateways;

import co.com.bancolombia.model.service.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface ServiceRepository {
    Mono<Service> findById(Integer id);

    Mono<Service> save(Service service);

    Flux<Service> findAllServicesPage(int size, int page);

    Flux<Service> findByTechnicianIdStartDateAndEndDate(int technicianId, LocalDateTime startDate, LocalDateTime endDate);
}
