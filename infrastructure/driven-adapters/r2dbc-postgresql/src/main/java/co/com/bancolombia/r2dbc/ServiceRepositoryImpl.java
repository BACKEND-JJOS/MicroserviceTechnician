package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.service.Service;
import co.com.bancolombia.model.service.gateways.ServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class ServiceRepositoryImpl implements ServiceRepository {

    private ServiceReactiveRepositoryAdapter dao;


    @Override
    public Mono<Service> findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public Mono<Service> save(Service service) {
        return dao.save(service);
    }

    @Override
    public Flux<Service> findAllServicesPage(int size, int page) {
        return dao.findAllBy(PageRequest.of(size, page));
    }
}
