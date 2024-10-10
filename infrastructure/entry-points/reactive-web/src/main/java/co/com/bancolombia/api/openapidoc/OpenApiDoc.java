package co.com.bancolombia.api.openapidoc;

import co.com.bancolombia.api.request.ServiceRequest;
import co.com.bancolombia.api.request.TechnicianRequest;
import co.com.bancolombia.api.response.ApiResponse;
import lombok.experimental.UtilityClass;
import org.springdoc.core.fn.builders.operation.Builder;
import org.springframework.http.HttpStatus;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.arrayschema.Builder.arraySchemaBuilder;
import static org.springdoc.core.fn.builders.content.Builder.contentBuilder;
import static org.springdoc.core.fn.builders.parameter.Builder.parameterBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.core.fn.builders.schema.Builder.schemaBuilder;
@UtilityClass
public class OpenApiDoc {
    private static  final  String MEDIA_TYPE_APPLICATION_JSON = "application/json";

    private static final String TAG_SERVICE = "Service";

    public Builder createService(Builder builder){
        return builder.operationId("createService")
                .description("Created a new service")
                .requestBody(
                        requestBodyBuilder()
                                .required(true)
                                .content(
                                        contentBuilder()
                                                .mediaType(MEDIA_TYPE_APPLICATION_JSON)
                                                .schema(schemaBuilder().implementation(ServiceRequest.class)
                                                )
                                )
                )
                .response(
                        responseBuilder()
                                .responseCode(HttpStatus.CREATED.toString())
                                .description("Service created succesfully")
                                .content(
                                        contentBuilder()
                                                .mediaType(MEDIA_TYPE_APPLICATION_JSON)
                                                .schema(schemaBuilder().implementation(ApiResponse.class))
                                )
                )
                .tag(TAG_SERVICE);
    }

    public Builder getAllServices(Builder builder){
        return builder.operationId("getAllServices")
                .description("Get all services paginate")
                .parameter(parameterBuilder()
                        .example("0")
                        .name("size")
                        .description("Location of the current page that is needed [starts at 0]")
                )
                .parameter(parameterBuilder()
                        .example("10")
                        .name("page")
                        .description("Number of records you want to see per page")
                )
                .response(
                        responseBuilder()
                                .responseCode(HttpStatus.OK.toString())
                                .description("List of paged services")
                                .content(
                                        contentBuilder()
                                                .mediaType(MEDIA_TYPE_APPLICATION_JSON)
                                                .array(
                                                        arraySchemaBuilder().schema(
                                                                schemaBuilder().implementation(ApiResponse.class)
                                                        )
                                                )
                                )
                )
                .tag(TAG_SERVICE);
    }

    public Builder getServiceById(Builder builder) {
        return builder.operationId("getServiceById")
                .description("Retrieve a service by ID")
                .parameter(parameterBuilder()
                        .name("Id")
                        .description("The Id of the service to retrieve")
                )
                .response(
                        responseBuilder()
                                .responseCode(HttpStatus.OK.toString())
                                .description("Service found successfully")
                                .content(
                                        contentBuilder()
                                                .mediaType(MEDIA_TYPE_APPLICATION_JSON)
                                                .schema(schemaBuilder().implementation(ApiResponse.class))
                                )
                )
                .response(
                        responseBuilder()
                                .responseCode(HttpStatus.NOT_FOUND.toString())
                                .description("Service not found")
                                .content(
                                        contentBuilder()
                                                .mediaType(MEDIA_TYPE_APPLICATION_JSON)
                                                .schema(schemaBuilder().implementation(String.class))
                                )
                )
                .tag(TAG_SERVICE);
    }

    public Builder createTechnician(Builder builder){
        return builder.operationId("createTechnician")
                .description("Created a new technician")
                .requestBody(
                        requestBodyBuilder()
                                .required(true)
                                .content(
                                        contentBuilder()
                                                .mediaType(MEDIA_TYPE_APPLICATION_JSON)
                                                .schema(schemaBuilder().implementation(TechnicianRequest.class)
                                                )
                                )
                )
                .response(
                        responseBuilder()
                                .responseCode(HttpStatus.CREATED.toString())
                                .description("User created succesfully")
                                .content(
                                        contentBuilder()
                                                .mediaType(MEDIA_TYPE_APPLICATION_JSON)
                                                .schema(schemaBuilder().implementation(ApiResponse.class))
                                )
                )

                .tag("Technician");
    }
}
