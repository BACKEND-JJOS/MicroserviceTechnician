package co.com.bancolombia.api.openapidoc;

import co.com.bancolombia.model.service.Service;
import lombok.experimental.UtilityClass;
import org.springdoc.core.fn.builders.operation.Builder;
import org.springframework.http.HttpStatus;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.arrayschema.Builder.arraySchemaBuilder;
import static org.springdoc.core.fn.builders.content.Builder.contentBuilder;
import static org.springdoc.core.fn.builders.schema.Builder.schemaBuilder;

@UtilityClass
public class OpenApiDoc {
    private static  final  String MEDIA_TYPE_APPLICATION_JSON = "application/json";

    public Builder getAllServices(Builder builder){
        return builder.operationId("getAllServices")
                .description("Get all services paginate")
                .response(
                        responseBuilder()
                                .responseCode(HttpStatus.OK.toString())
                                .description("List Services ")
                                .content(
                                        contentBuilder()
                                                .mediaType(MEDIA_TYPE_APPLICATION_JSON)
                                                .array(
                                                        arraySchemaBuilder().schema(
                                                                schemaBuilder().implementation(Service.class)
                                                        )
                                                )
                                )
                )
                .tag("Service");
    }
}
