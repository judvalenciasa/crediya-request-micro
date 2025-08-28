package co.com.crediyarequest.api.responsedto;

public record ApplicationResponseDto(
         Long idRequest,
         Long idState,
         Long longTypeId,
         double amount,
         int term,
         String document
) {

}
