package co.com.bancolombia.api;

import co.com.bancolombia.api.openapidoc.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;


import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;

@Configuration
public class RouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return  route()
                .GET("/service/all", handler::listenGETAllService, OpenAPI::getAllServices)
                .GET("/service/{id}",handler::listenGETServiceById, OpenAPI::getAllServices)
                .POST("/technician",handler::listenPOSTCreateTechnician, OpenAPI::getAllServices)
                .POST("/service", handler::listenPOSTCreateNewService, OpenAPI::getAllServices)
                .build();
    }
}
