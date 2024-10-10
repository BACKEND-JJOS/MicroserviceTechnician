package co.com.bancolombia.usecase.getallpaginatedservices;

import co.com.bancolombia.model.service.Service;
import co.com.bancolombia.model.service.gateways.ServiceRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class GetAllPaginatedServicesUseCase {

    private  final ServiceRepository serviceRepository;
    public Flux<Service> getAll(int size, int page){
        return serviceRepository.findAllServicesPage(size,page);
    }
}
