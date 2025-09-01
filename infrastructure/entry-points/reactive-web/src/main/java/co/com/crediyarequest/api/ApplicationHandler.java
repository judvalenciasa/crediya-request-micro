package co.com.crediyarequest.api;

import co.com.crediyarequest.api.exception.ValidationExceptionDto;
import co.com.crediyarequest.api.handler.GlobalExceptionHandler;
import co.com.crediyarequest.api.mapper.ApplicationMapper;
import co.com.crediyarequest.api.requestdto.application.ApplicationCreateRequestDto;
import co.com.crediyarequest.usecase.aplication.IApplicationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;


import reactor.core.publisher.Mono;
@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationHandler {
    private final IApplicationUseCase iApplicationUseCase;
    private final GlobalExceptionHandler globalExceptionHandler;
    private final Validator validator;
    private final ApplicationMapper applicationMapper;

    public Mono<ServerResponse> createApplication(ServerRequest serverRequest) {
        log.info("event=APPLICATION_CREATION_INITIATED");
        return serverRequest.bodyToMono(ApplicationCreateRequestDto.class)
                .flatMap(dto -> {
                    Errors errors = new BeanPropertyBindingResult(dto, ApplicationCreateRequestDto.class.getName());
                    validator.validate(dto, errors);

                    if (errors.hasErrors()) {
                        return Mono.error(new ValidationExceptionDto(errors));
                    }

                    log.info("Use case starting");
                    return iApplicationUseCase.saveApplication(applicationMapper.toEntity(dto));
                })
                .doOnNext(savedApplication -> log.info("event=APPLICATION_SAVED_SUCCESSFULLY, application={}", savedApplication))
                .map(applicationMapper::toDto)
                .doOnNext(responseDto -> log.info("event=RESPONSE_DTO_GENERATED, response={}", responseDto))
                .flatMap(applicationResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(applicationResponse))
                .doOnSuccess(response -> log.info("event=APPLICATION_CREATION_COMPLETED"))
                .onErrorResume(globalExceptionHandler::handleError);
    }


}