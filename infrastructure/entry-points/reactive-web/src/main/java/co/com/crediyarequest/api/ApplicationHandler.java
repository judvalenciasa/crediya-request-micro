package co.com.crediyarequest.api;

import co.com.crediyarequest.api.exception.ValidationExceptionDto;
import co.com.crediyarequest.api.handler.GlobalExceptionHandler;
import co.com.crediyarequest.api.mapper.ApplicationMapper;
import co.com.crediyarequest.api.requestdto.ApplicationRequestDto;
import co.com.crediyarequest.usecase.aplication.IApplicationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ApplicationHandler {
    private final IApplicationUseCase iApplicationUseCase;
    private final GlobalExceptionHandler globalExceptionHandler;
    private final Validator validator;

    public Mono<ServerResponse> createApplication(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(ApplicationRequestDto.class)
                .flatMap(dto -> {
                    Errors errors = new BeanPropertyBindingResult(dto, ApplicationRequestDto.class.getName());
                    validator.validate(dto, errors);

                    if (errors.hasErrors()) {
                        return Mono.error(new ValidationExceptionDto(errors));
                    }

                    return iApplicationUseCase.saveApplication(ApplicationMapper.toEntity(dto));
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