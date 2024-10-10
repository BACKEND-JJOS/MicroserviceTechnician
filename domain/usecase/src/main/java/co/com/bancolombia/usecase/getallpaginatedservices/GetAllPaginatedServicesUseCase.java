package co.com.bancolombia.usecase.getallpaginatedservices;

import co.com.bancolombia.model.service.Service;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class GetAllPaginatedServicesUseCase {

    public Flux<Service> getAll(int size, int offset){
        return Flux.just(Service.builder()
                .address("Conjunto Entre Lomas Torre 11 apto 303")
                .build()
        );
    }
}
