package co.com.bancolombia.model.technician.gateways;

import co.com.bancolombia.model.technician.Technician;
import reactor.core.publisher.Mono;

public interface TechnicianRepository {
    Mono<Technician> findById(Integer id);
    Mono<Technician> save(Technician technician);

}
