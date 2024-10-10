package co.com.bancolombia.usecase.createnewtechnician;

import co.com.bancolombia.model.technician.Technician;
import co.com.bancolombia.model.technician.gateways.TechnicianRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateNewTechnicianUseCaseTest {

    @Mock
    private TechnicianRepository technicianRepository;

    @InjectMocks
    private CreateNewTechnicianUseCase createNewTechnicianUseCase;

    @Test
    void shouldCreateNewTechnician_whenSendTechnician() {
        Technician mockTechnician = Technician.builder()
                .id(1)
                .name("any name")
                .build();

        when(technicianRepository.save(any(Technician.class)))
                .thenReturn(Mono.just(mockTechnician));

        StepVerifier.create(createNewTechnicianUseCase.create(mockTechnician))
                .expectNextMatches(technician -> technician.getId() == 1 && technician.getName().equals("any name"))
                .verifyComplete();

        verify(technicianRepository).save(any());
    }
}

