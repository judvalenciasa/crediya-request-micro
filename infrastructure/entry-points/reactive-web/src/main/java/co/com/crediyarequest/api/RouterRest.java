package co.com.crediyarequest.api;

import co.com.crediyarequest.api.exception.ValidationExceptionDto;
import co.com.crediyarequest.api.handler.GlobalExceptionHandler;
import co.com.crediyarequest.api.mapper.ApplicationMapper;
import co.com.crediyarequest.api.requestdto.ApplicationRequestDto;
import co.com.crediyarequest.usecase.aplication.IApplicationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@RequiredArgsConstructor
public class RouterRest {
    private final IApplicationUseCase iApplicationUseCase;
    private final ApplicationMapper userMapper;
    private final GlobalExceptionHandler globalExceptionHandler;
    private final Validator validator;

    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(ApplicationRequestDto.class)
                .flatMap(dto -> {
                    Errors errors = new BeanPropertyBindingResult(dto, ApplicationRequestDto.class.getName());
                    validator.validate(dto, errors);

                    if (errors.hasErrors()) {
                        return Mono.error(new ValidationExceptionDto(errors));
                    }

                    return iApplicationUseCase.saveApplication(userMapper.toEntity(dto));
                })
                .map(ApplicationMapper::toDto)
                .flatMap(userResponse ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(userResponse)
                )
                .onErrorResume(globalExceptionHandler::handleError);

    }
}
