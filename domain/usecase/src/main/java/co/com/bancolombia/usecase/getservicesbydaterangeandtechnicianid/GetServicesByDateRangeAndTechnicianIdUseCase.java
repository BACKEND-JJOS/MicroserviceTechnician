package co.com.bancolombia.usecase.getservicesbydaterangeandtechnicianid;

import co.com.bancolombia.model.service.Service;
import co.com.bancolombia.model.service.gateways.ServiceRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class GetServicesByDateRangeAndTechnicianIdUseCase {

    private final ServiceRepository serviceRepository;
    public Flux<Service> getServices(int technicianId, LocalDateTime startDate , LocalDateTime endDate) {
        return serviceRepository.findByTechnicianIdStartDateAndEndDate(technicianId, startDate, endDate);
    }
}
