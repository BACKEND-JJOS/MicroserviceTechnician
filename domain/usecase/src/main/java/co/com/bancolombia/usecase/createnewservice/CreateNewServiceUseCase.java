package co.com.bancolombia.usecase.createnewservice;

import co.com.bancolombia.model.service.Service;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateNewServiceUseCase {
    public Mono<Service> create(Service service) {
        return Mono.just(service);
    }
}
