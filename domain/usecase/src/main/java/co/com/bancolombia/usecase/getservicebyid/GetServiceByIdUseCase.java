package co.com.bancolombia.usecase.getservicebyid;

import co.com.bancolombia.model.service.Service;
import co.com.bancolombia.model.service.gateways.ServiceRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetServiceByIdUseCase {

    private final ServiceRepository serviceRepository;
    public Mono<Service> getService(Integer id){
        return serviceRepository.findById(id);
    }
}
