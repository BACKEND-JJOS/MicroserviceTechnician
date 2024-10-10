package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.service.Service;
import co.com.bancolombia.model.service.gateways.ServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class ServiceRepositoryImpl implements ServiceRepository {

    private MyReactiveRepositoryAdapter daoService;


    @Override
    public Mono<Service> findById(Integer id) {
        return daoService.findById(id);
    }

    @Override
    public Mono<Service> save(Service service) {
        return daoService.save(service);
    }
}
