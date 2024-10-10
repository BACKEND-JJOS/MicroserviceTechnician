package co.com.bancolombia.usecase.getservicebyid;

import co.com.bancolombia.model.service.Service;
import co.com.bancolombia.model.service.gateways.ServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetServiceByIdUseCaseTest {

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private GetServiceByIdUseCase getServiceByIdUseCase;

    @Test
    void shouldGetServiceById_whenServiceExists() {
        Service mockService = Service.builder().id(1).build();

        when(serviceRepository.findById(anyInt()))
                .thenReturn(Mono.just(mockService));

        StepVerifier.create(getServiceByIdUseCase.getService(anyInt()))
                .expectNext(mockService)
                .verifyComplete();
    }

    @Test
    void shouldReturnEmpty_whenServiceDoesNotExist() {
        when(serviceRepository.findById(anyInt()))
                .thenReturn(Mono.empty());

        StepVerifier.create(getServiceByIdUseCase.getService(999))
                .verifyComplete();
    }
}
