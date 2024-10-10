package co.com.bancolombia.usecase.createnewservice;

import co.com.bancolombia.model.service.Service;
import co.com.bancolombia.model.service.gateways.ServiceRepository;
import co.com.bancolombia.model.technician.Technician;
import co.com.bancolombia.model.technician.gateways.TechnicianRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateNewServiceUseCase {

    private final ServiceRepository serviceRepository;

    private  final TechnicianRepository technicianRepository;
    public Mono<Service> create(Service service) {
        return serviceRepository.save(service);
    }
}
