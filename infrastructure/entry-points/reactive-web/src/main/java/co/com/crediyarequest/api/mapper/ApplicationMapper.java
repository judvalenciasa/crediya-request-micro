package co.com.crediyarequest.api.mapper;

import co.com.crediyarequest.api.requestdto.ApplicationRequestDto;
import co.com.crediyarequest.model.application.Application;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public interface ApplicationMapper {

    static Application toEntity(Application application) {
        return new Application(
                application.getAmount(),
                application.getTerm(),
                application.getDocument()
        );
    }

    static ApplicationRequestDto toDto(Application application) {
        return new ApplicationRequestDto(
                application.getAmount(),
                application.getTerm(),
                application.getDocument()
        );
    }
}

