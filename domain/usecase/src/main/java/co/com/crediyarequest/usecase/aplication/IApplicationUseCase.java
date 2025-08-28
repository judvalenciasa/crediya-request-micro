package co.com.crediyarequest.usecase.aplication;

import co.com.crediyarequest.model.application.Application;
import reactor.core.publisher.Mono;

public interface IApplicationUseCase {

    Mono<Application> saveApplication(Application application) ;
}
