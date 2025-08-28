package co.com.crediyarequest.usecase.aplication;


import co.com.crediyarequest.model.application.Application;
import co.com.crediyarequest.model.application.gateways.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ApplicationUseCase implements IApplicationUseCase {
    private final ApplicationRepository applicationRepository;

    @Override
    public Mono<Application> saveApplication(Application application) {
        return null;
    }
}
