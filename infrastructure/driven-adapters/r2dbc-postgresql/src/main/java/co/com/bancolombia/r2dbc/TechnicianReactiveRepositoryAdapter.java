package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.technician.Technician;
import co.com.bancolombia.r2dbc.entity.TechnicianEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import com.google.gson.Gson;
import org.springframework.stereotype.Repository;

@Repository
public class TechnicianReactiveRepositoryAdapter extends ReactiveAdapterOperations<Technician, TechnicianEntity, Integer, TechnicianReactiveRepository>
{
    public TechnicianReactiveRepositoryAdapter(TechnicianReactiveRepository repository, Gson gson) {
        super(repository, gson, Technician.class);
    }

}
