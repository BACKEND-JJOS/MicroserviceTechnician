package co.com.bancolombia.api;

import co.com.bancolombia.api.request.ServiceRequest;
import co.com.bancolombia.api.request.TechnicianRequest;
import co.com.bancolombia.api.response.ApiResponse;
import co.com.bancolombia.model.service.Service;
import co.com.bancolombia.model.technician.Technician;
import co.com.bancolombia.usecase.createnewservice.CreateNewServiceUseCase;
import co.com.bancolombia.usecase.createnewtechnician.CreateNewTechnicianUseCase;
import co.com.bancolombia.usecase.getallpaginatedservices.GetAllPaginatedServicesUseCase;
import co.com.bancolombia.usecase.getservicebyid.GetServiceByIdUseCase;
import co.com.bancolombia.usecase.getservicesbydaterangeandtechnicianid.GetServicesByDateRangeAndTechnicianIdUseCase;
import config.TestConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

@ContextConfiguration(classes = {RouterRest.class, Handler.class, TestConfig.class})
@WebFluxTest
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private GetAllPaginatedServicesUseCase getAllPaginatedServicesUseCase;

    @MockBean
    private GetServiceByIdUseCase getServiceByIdUseCase;

    @MockBean
    private CreateNewServiceUseCase createNewServiceUseCase;

    @MockBean
    private CreateNewTechnicianUseCase createNewTechnicianUseCase;

    @MockBean
    private GetServicesByDateRangeAndTechnicianIdUseCase getServicesByDateRangeAndTechnicianIdUseCase;
    @Test
    void shouldListAllServices_shouldReturnOk_whenServicesExist() {
        Service mockService1 = Service.builder()
                .address("any direction 2")
                .description("any description")
                .endDate(LocalDateTime.now())
                .startDate(LocalDateTime.now())
                .technicianId(Integer.valueOf(1))
                .build();

        Service mockService2 = Service.builder()
                .address("any direction 2")
                .description("any description 2")
                .endDate(LocalDateTime.now())
                .startDate(LocalDateTime.now())
                .technicianId(Integer.valueOf(1))
                .build();
        given(getAllPaginatedServicesUseCase.getAll(anyInt(), anyInt()))
                .willReturn(Flux.just(mockService1, mockService2));

        webTestClient.get()
                .uri("/service/all?size=0&page=10")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ApiResponse.class)
                .value(apiResponse -> {
                    Assertions.assertThat(apiResponse.getData()).isNotNull();
                });

        Mockito.verify(getAllPaginatedServicesUseCase).getAll(anyInt(),anyInt());

    }

    @Test
    void shouldReturnServiceById_whenServiceExists() {
        Service mockService = Service.builder()
                .address("any direction")
                .description("any description")
                .endDate(LocalDateTime.now())
                .startDate(LocalDateTime.now())
                .technicianId(1)
                .build();

        given(getServiceByIdUseCase.getService(anyInt()))
                .willReturn(Mono.just(mockService));

        webTestClient.get()
                .uri("/service/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ApiResponse.class)
                .value(apiResponse -> {
                    Assertions.assertThat(apiResponse.getData()).isNotNull();
                });

        Mockito.verify(getServiceByIdUseCase).getService(anyInt());
    }

    @Test
    void shouldReturnNotFound_whenServiceDoesNotExist() {
        given(getServiceByIdUseCase.getService(anyInt()))
                .willReturn(Mono.empty());

        webTestClient.get()
                .uri("/service/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ApiResponse.class)
                .value(apiResponse -> {
                    Assertions.assertThat(apiResponse.getMessage()).isEqualTo("The service does not exist");
                });

        Mockito.verify(getServiceByIdUseCase).getService(anyInt());
    }

    @Test
    void shouldCreateNewService_whenValidRequest() {
        ServiceRequest request = ServiceRequest.builder()
                .address("any direction")
                .description("any description")
                .technicianId(Long.valueOf(1))
                .endDate(LocalDateTime.now())
                .startDate(LocalDateTime.now())
                .build();

        Service mockService = Service.builder()
                .address(request.getAddress())
                .description(request.getDescription())
                .endDate(request.getEndDate())
                .startDate(request.getStartDate())
                .technicianId(request.getTechnicianId().intValue())
                .build();

        given(createNewServiceUseCase.create(any()))
                .willReturn(Mono.just(mockService));

        webTestClient.post()
                .uri("/service")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ApiResponse.class)
                .value(apiResponse -> {
                    Assertions.assertThat(apiResponse.getData()).isNotNull();
                });

        Mockito.verify(createNewServiceUseCase).create(any(Service.class));
    }

    @Test
    void shouldCreateNewTechnician_whenValidRequest() {
        TechnicianRequest request = TechnicianRequest.builder()
                .name("any direction")
                .build();

        Technician mockTechnician = Technician.builder()
                .name(request.getName())
                .build();

        given(createNewTechnicianUseCase.create(any()))
                .willReturn(Mono.just(mockTechnician));

        webTestClient.post()
                .uri("/technician")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ApiResponse.class)
                .value(apiResponse -> {
                    Assertions.assertThat(apiResponse.getData()).isNotNull();
                });

        Mockito.verify(createNewTechnicianUseCase).create(any(Technician.class));
    }

    /*@Test
    void shouldReturnBadRequest_whenServiceRequestIsInvalid() {
        ServiceRequest request = ServiceRequest.builder()
                .address("any address with more than 30 characters")
                .description("any description")
                .technicianId(Long.valueOf(1))
                .endDate(LocalDateTime.now())
                .startDate(LocalDateTime.now())
                .build();


        webTestClient.post()
                .uri("/service")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest();

        Mockito.verify(createNewServiceUseCase, Mockito.never()).create(any(Service.class));
    }*/

}
