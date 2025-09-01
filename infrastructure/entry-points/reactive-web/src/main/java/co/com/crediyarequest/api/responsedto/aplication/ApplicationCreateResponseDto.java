package co.com.crediyarequest.api.responsedto.aplication;

public record ApplicationCreateResponseDto(
         Long idRequest,
         Long stateId,
         Long loantypeId,
         double amount,
         int term,
         String document
) {

}
