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

    private static final String RESPONSE_OK = "Response Ok";

    public Mono<ServerResponse> listenPOSTCreateTechnician(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(TechnicianRequest.class)
                .flatMap(GenericValidator::validate)
                .map(technicianRequest -> mapper.fromJson(mapper.toJson(technicianRequest), Technician.class))
                .flatMap(technician -> createNewTechnicianUseCase.create(technician)
                        .flatMap(savedTechnician ->  buildResponse(savedTechnician, HttpStatus.CREATED.value(), RESPONSE_OK))
                );
    }

    //TODO: Como validar cuando siz y page no sea un valor numerico ?
    public Mono<ServerResponse> listenGETAllService(ServerRequest serverRequest) {
        return Mono.just(serverRequest)
                .flatMap(req -> Mono.zip(
                        Mono.justOrEmpty(req.queryParam("size")).map(Integer::parseInt).defaultIfEmpty(0),
                        Mono.justOrEmpty(req.queryParam("page")).map(Integer::parseInt).defaultIfEmpty(10)
                ))
                .flatMap(tuple -> getAllPaginatedServicesUseCase.getAll(tuple.getT1(), tuple.getT2())
                        .collectList()
                        .flatMap(services -> buildResponse(services, HttpStatus.OK.value(), RESPONSE_OK))
                        .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error searching services: " + e.getMessage())));
    }

    public Mono<ServerResponse> listenGETServiceById(ServerRequest serverRequest) {
        return getServiceByIdUseCase.getService(Integer.valueOf(serverRequest.pathVariable("id")))
                        .flatMap(service -> buildResponse(service, HttpStatus.OK.value(), RESPONSE_OK))
                        .switchIfEmpty(buildResponse(null, HttpStatus.NOT_FOUND.value(), "The service does not exist"))
                        .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error searching services: " + e.getMessage()));
    }

    public Mono<ServerResponse> listenPOSTCreateNewService(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(ServiceRequest.class)
                .flatMap(GenericValidator::validate)
                .map(serviceRequest -> mapper.fromJson(mapper.toJson(serviceRequest), Service.class))
                .flatMap(service -> createNewServiceUseCase.create(service)
                        .flatMap(savedService -> buildResponse(savedService, HttpStatus.CREATED.value(), RESPONSE_OK))
                );
    }

    public Mono<ServerResponse> listenGETServiceByDateRangeAndTechnicianId(ServerRequest serverRequest) {

        return Mono.just(serverRequest)
                .flatMap(req -> Mono.zip(
                        Mono.justOrEmpty(req.queryParam("technicianId")).map(Integer::parseInt).defaultIfEmpty(0),
                        Mono.justOrEmpty(req.queryParam("page")).map(Integer::parseInt).defaultIfEmpty(0),
                        Mono.justOrEmpty(req.queryParam("size")).map(Integer::parseInt).defaultIfEmpty(10)
                ))
                .flatMap(tuple -> getAllPaginatedServicesUseCase.getAll(tuple.getT1(), tuple.getT2())
                        .collectList()
                        .flatMap(services -> buildResponse(services, HttpStatus.OK.value(), RESPONSE_OK))
                        .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error searching services: " + e.getMessage()))
                );
    }

    private <T> Mono<ServerResponse> buildResponse(T data, int status, String message) {
        return ServerResponse.status(status).bodyValue(
                ApiResponse.<T>builder()
                        .data(data)
                        .status(status)
                        .message(message)
                        .build()
        );
    }
}
