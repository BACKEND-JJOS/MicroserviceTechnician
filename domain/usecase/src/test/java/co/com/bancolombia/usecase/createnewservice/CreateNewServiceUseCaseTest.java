package co.com.bancolombia.usecase.createnewservice;

import co.com.bancolombia.model.service.Service;
import co.com.bancolombia.model.service.gateways.ServiceRepository;
import co.com.bancolombia.model.technician.Technician;
import co.com.bancolombia.model.technician.gateways.TechnicianRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CreateNewServiceUseCaseTest {

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private TechnicianRepository technicianRepository;

    @InjectMocks
    private CreateNewServiceUseCase createNewServiceUseCase;

    @Test
    void shouldCreateNewSerivce_whenSendNewService(){
        Service mockService = Service.builder()
                .id(1)
                .address("any direction")
                .description("any description")
                .endDate(LocalDateTime.now())
                .startDate(LocalDateTime.now())
                .technicianId(1)
                .build();

        Technician mockTechnician = Technician.builder()
                .id(1)
                .name("any name")
                .build();

        when(technicianRepository.findById(1))
                .thenReturn(Mono.just(mockTechnician));

        when(serviceRepository.save(any()))
                .thenReturn(Mono.just(mockService));

        StepVerifier.create(createNewServiceUseCase.create(mockService))
                .assertNext(service -> {
                    assertNotNull(service);
                    assertEquals(1, service.getId());
                })
                .verifyComplete();

        verify(serviceRepository).save(any());
    }
}
