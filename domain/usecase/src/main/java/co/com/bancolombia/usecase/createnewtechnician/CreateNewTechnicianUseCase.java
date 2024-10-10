package co.com.bancolombia.usecase.createnewtechnician;

import co.com.bancolombia.model.technician.Technician;
import co.com.bancolombia.model.technician.gateways.TechnicianRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateNewTechnicianUseCase {

    private final TechnicianRepository technicianRepository;

    public Mono<Technician> create(Technician technician) {
        return technicianRepository.save(technician);
    }
}
