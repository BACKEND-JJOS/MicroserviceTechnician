package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.service.Service;
import co.com.bancolombia.r2dbc.entity.ServiceEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import com.google.gson.Gson;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;



@Repository
public class ServiceReactiveRepositoryAdapter extends ReactiveAdapterOperations<Service, ServiceEntity, Integer, ServiceReactiveRepository>
{
    public ServiceReactiveRepositoryAdapter(ServiceReactiveRepository repository, Gson gson) {
        super(repository, gson, Service.class);
    }

    public Flux<Service> findAllBy(Pageable pageable) {
        return repository.findAllBy(pageable).map(this::toEntity);
    }

}
