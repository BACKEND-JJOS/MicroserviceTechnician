package co.com.bancolombia.api;

import co.com.bancolombia.api.request.ServiceRequest;
import co.com.bancolombia.api.request.TechnicianRequest;
import co.com.bancolombia.api.response.ApiResponse;
import co.com.bancolombia.api.validator.GenericValidator;
import co.com.bancolombia.model.service.Service;
import co.com.bancolombia.model.technician.Technician;
import co.com.bancolombia.usecase.createnewservice.CreateNewServiceUseCase;
import co.com.bancolombia.usecase.createnewtechnician.CreateNewTechnicianUseCase;
import co.com.bancolombia.usecase.getallpaginatedservices.GetAllPaginatedServicesUseCase;
import co.com.bancolombia.usecase.getservicebyid.GetServiceByIdUseCase;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


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
                        .flatMap(savedTechnician ->  buildResponse(savedTechnician, HttpStatus.CREATED.value(), "Response ok"))
                );
    }

    public Mono<ServerResponse> listenGETAllService(ServerRequest serverRequest) {
        int page = Integer.parseInt(serverRequest.queryParam("page").orElse("0"));
        int size = Integer.parseInt(serverRequest.queryParam("size").orElse("10"));
        return getAllPaginatedServicesUseCase.getAll(size,page)
                .collectList()
                .flatMap(services ->  buildResponse(services, HttpStatus.OK.value(), "Response ok"))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error searching services: " + e.getMessage()));
    }

    public Mono<ServerResponse> listenGETServiceById(ServerRequest serverRequest) {
        return getServiceByIdUseCase.getService(Integer.valueOf(serverRequest.pathVariable("id")))
                        .flatMap(service -> buildResponse(service, HttpStatus.OK.value(), "Response ok"))
                        .switchIfEmpty(buildResponse(null, HttpStatus.NOT_FOUND.value(), "The service does not exist"))
                        .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error searching services: " + e.getMessage()));
    }

    public Mono<ServerResponse> listenPOSTCreateNewService(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(ServiceRequest.class)
                .flatMap(GenericValidator::validate)
                .map(serviceRequest -> mapper.fromJson(mapper.toJson(serviceRequest), Service.class))
                .flatMap(service -> createNewServiceUseCase.create(service)
                        .flatMap(savedService -> buildResponse(savedService, HttpStatus.CREATED.value(), "Response ok"))
                );
    }

    private <T> Mono<ServerResponse> buildResponse(T data, int status, String message) {
        ApiResponse<T> response = ApiResponse.<T>builder()
                .data(data)
                .status(status)
                .message(message)
                .build();
        return ServerResponse.status(status).bodyValue(response);
    }

}
