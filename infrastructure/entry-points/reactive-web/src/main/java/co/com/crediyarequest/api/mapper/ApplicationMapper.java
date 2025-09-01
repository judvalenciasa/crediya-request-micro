package co.com.crediyarequest.api.mapper;


import co.com.crediyarequest.api.requestdto.application.ApplicationCreateRequestDto;
import co.com.crediyarequest.api.responsedto.aplication.ApplicationCreateResponseDto;
import co.com.crediyarequest.model.application.Application;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    Application toEntity(ApplicationCreateRequestDto dto);

    ApplicationCreateResponseDto toDto(Application application);

}

