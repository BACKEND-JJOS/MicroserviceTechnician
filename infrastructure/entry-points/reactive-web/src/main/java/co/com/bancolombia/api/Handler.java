package co.com.bancolombia.api;

import co.com.bancolombia.api.request.ServiceRequest;
import co.com.bancolombia.api.request.TechnicianRequest;
import co.com.bancolombia.api.validator.GenericValidator;
import co.com.bancolombia.model.service.Service;
import co.com.bancolombia.model.technician.Technician;
import co.com.bancolombia.usecase.createnewservice.CreateNewServiceUseCase;
import co.com.bancolombia.usecase.createnewtechnician.CreateNewTechnicianUseCase;
import co.com.bancolombia.usecase.getallpaginatedservices.GetAllPaginatedServicesUseCase;
import co.com.bancolombia.usecase.getservicebyid.GetServiceByIdUseCase;
import com.google.gson.Gson;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Handler {

    private final GetAllPaginatedServicesUseCase getAllPaginatedServicesUseCase;

    private final GetServiceByIdUseCase getServiceByIdUseCase;

    private final CreateNewServiceUseCase createNewServiceUseCase;

    private final CreateNewTechnicianUseCase createNewTechnicianUseCase;

    private  final Gson mapper;

    public Mono<ServerResponse> listenPOSTCreateTechnician(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(TechnicianRequest.class)
                .flatMap(GenericValidator::validate)
                .map(technicianRequest -> mapper.fromJson(mapper.toJson(technicianRequest), Technician.class))
                .flatMap(technician -> createNewTechnicianUseCase.create(technician)
                        .flatMap(savedService -> ServerResponse.ok().bodyValue(savedService))
                )
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error saving technician: " + e.getMessage()));
    }

    public Mono<ServerResponse> listenGETAllService(ServerRequest serverRequest) {
        return getAllPaginatedServicesUseCase.getAll(0,0)
                .collectList()
                .flatMap(services -> ServerResponse.ok().bodyValue(services))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error searching services: " + e.getMessage()));
    }

    public Mono<ServerResponse> listenGETServiceById(ServerRequest serverRequest) {

        return getServiceByIdUseCase.getService(Integer.valueOf(serverRequest.pathVariable("id")))
                        .flatMap(services -> ServerResponse.ok().bodyValue(services))
                        .switchIfEmpty(ServerResponse.notFound().build())
                        .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error searching services: " + e.getMessage()));
    }

    public Mono<ServerResponse> listenPOSTCreateNewService(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(ServiceRequest.class)
                .flatMap(GenericValidator::validate)
                .map(serviceRequest -> mapper.fromJson(mapper.toJson(serviceRequest), Service.class))
                .flatMap(service -> createNewServiceUseCase.create(service)
                        .flatMap(savedService -> ServerResponse.ok().bodyValue(savedService))
                );
    }
}
