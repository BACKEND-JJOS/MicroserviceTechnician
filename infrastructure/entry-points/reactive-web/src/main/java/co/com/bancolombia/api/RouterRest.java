package co.com.bancolombia.api;

import co.com.bancolombia.api.openapidoc.OpenApiDoc;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.queryParam;
import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;

@Configuration
public class RouterRest {

    @Bean
    public WebProperties.Resources  resources(){return new WebProperties.Resources();}

    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return  route()
                .GET("/service/all",
                        RequestPredicates.queryParam("page", page -> true)
                                .and(queryParam("size", size ->true)),
                        handler::listenGETAllService,
                        OpenApiDoc::getAllServices
                )
                .GET("/service/filter-by-technician-and-range-date",
                        RequestPredicates.queryParam("technicianId", technicianId -> true)
                                .and(queryParam("startDate", startDate -> true))
                                .and(queryParam("endDate", endDate -> true)),
                        handler::listenGETServiceByDateRangeAndTechnicianId,
                        OpenApiDoc::getAllServices
                )
                .GET("/service/{id}",handler::listenGETServiceById, OpenApiDoc::getServiceById)
                .POST("/technician",handler::listenPOSTCreateTechnician, OpenApiDoc::createTechnician)
                .POST("/service", handler::listenPOSTCreateNewService, OpenApiDoc::createService)
                .build();
    }
}
