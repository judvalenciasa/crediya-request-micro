package co.com.crediyarequest.api.mapper;

import co.com.crediyarequest.api.requestdto.ApplicationRequestDto;
import co.com.crediyarequest.model.application.Application;

public interface ApplicationMapper {

    static Application toEntity(ApplicationRequestDto applicationRequestDto) {
        return new Application(
                applicationRequestDto.idloanType(),
                applicationRequestDto.amount(),
                applicationRequestDto.term(),
                applicationRequestDto.document()
        );
    }

    static ApplicationRequestDto toDto(Application application) {
        return new ApplicationRequestDto(
                application.getIdloanType(),
                application.getAmount(),
                application.getTerm(),
                application.getDocument()
        );
    }
}

