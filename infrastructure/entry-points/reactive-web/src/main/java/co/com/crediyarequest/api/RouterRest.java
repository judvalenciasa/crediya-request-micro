package co.com.crediyarequest.api;

import co.com.crediyarequest.api.exception.ValidationExceptionDto;
import co.com.crediyarequest.api.handler.GlobalExceptionHandler;
import co.com.crediyarequest.api.mapper.ApplicationMapper;
import co.com.crediyarequest.api.requestdto.ApplicationRequestDto;
import co.com.crediyarequest.usecase.aplication.IApplicationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
public class RouterRest {
    private final ApplicationHandler applicationHandler;

    public RouterRest(ApplicationHandler applicationHandler) {
        this.applicationHandler = applicationHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> userRoutes() {
        return route()
                .POST("/api/v1/applications", applicationHandler::createApplication)
                .GET("/openapi/openapi.yaml", request ->
                        ServerResponse.ok()
                                .contentType(MediaType.parseMediaType("application/yaml"))
                                .bodyValue(new ClassPathResource("openapi/openapi.yaml"))
                )
                .build();
    }
}
