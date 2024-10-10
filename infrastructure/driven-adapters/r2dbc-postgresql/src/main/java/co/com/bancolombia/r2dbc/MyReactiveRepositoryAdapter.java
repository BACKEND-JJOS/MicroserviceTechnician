package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.service.Service;
import co.com.bancolombia.r2dbc.entity.ServiceEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import com.google.gson.Gson;
import org.springframework.stereotype.Repository;

@Repository
public class MyReactiveRepositoryAdapter extends ReactiveAdapterOperations<Service, ServiceEntity, Integer, MyReactiveRepository>
{
    public MyReactiveRepositoryAdapter(MyReactiveRepository repository, Gson gson) {
        super(repository, gson, Service.class);
    }

}
