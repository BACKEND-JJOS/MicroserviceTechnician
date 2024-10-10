package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.technician.Technician;
import co.com.bancolombia.model.technician.gateways.TechnicianRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class TechnicianRepositoryImpl implements TechnicianRepository {

    private TechnicianReactiveRepositoryAdapter dao;
    @Override
    public Mono<Technician> findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public Mono<Technician> save(Technician technician) {
        return dao.save(technician);
    }
}
