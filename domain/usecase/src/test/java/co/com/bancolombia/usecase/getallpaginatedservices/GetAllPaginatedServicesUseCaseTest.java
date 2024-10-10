package co.com.bancolombia.usecase.getallpaginatedservices;

import co.com.bancolombia.model.service.Service;
import co.com.bancolombia.model.service.gateways.ServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetAllPaginatedServicesUseCaseTest {

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private GetAllPaginatedServicesUseCase getAllPaginatedServicesUseCase;

    @Test
    void shouldGetAllServices_whenRequestWithPageAndSize() {
        Service mockService1 = Service.builder().id(1).build();
        Service mockService2 = Service.builder().id(2).build();

        when(serviceRepository.findAllServicesPage(anyInt(), anyInt()))
                .thenReturn(Flux.just(mockService1, mockService2));

        StepVerifier.create(getAllPaginatedServicesUseCase.getAll(anyInt(), anyInt()))
                .expectNext(mockService1, mockService2)
                .verifyComplete();
    }
}
