package co.com.crediyarequest.model.application.gateways;

import co.com.crediyarequest.model.application.Application;
import reactor.core.publisher.Mono;

public interface ApplicationRepository {
    Mono<Application> saveApplication(Application application) ;
}
