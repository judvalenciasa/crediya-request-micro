package co.com.crediyarequest.usecase.aplication;
import co.com.crediyarequest.model.application.Application;

public class ApplicationTestBuilder {

    private Long idRequest;
    private Long stateId;
    private Long loantypeId;
    private double amount = 100000.0;
    private int term = 12;
    private String document = "12345678";

    public ApplicationTestBuilder withIdRequest(Long idRequest) {
        this.idRequest = idRequest;
        return this;
    }

    public ApplicationTestBuilder withStateId(Long stateId) {
        this.stateId = stateId;
        return this;
    }

    public ApplicationTestBuilder withLoantypeId(Long loantypeId) {
        this.loantypeId = loantypeId;
        return this;
    }

    public ApplicationTestBuilder withAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public ApplicationTestBuilder withTerm(int term) {
        this.term = term;
        return this;
    }

    public ApplicationTestBuilder withDocument(String document) {
        this.document = document;
        return this;
    }

    public Application build() {
        Application application = Application.builder()
                .idRequest(idRequest)
                .stateId(stateId)
                .loantypeId(loantypeId)
                .amount(amount)
                .term(term)
                .document(document)
                .build();

        return application;
    }

    public static ApplicationTestBuilder anApplication() {
        return new ApplicationTestBuilder();
    }

    public static ApplicationTestBuilder anApplicationWithDefaults() {
        return new ApplicationTestBuilder()
                .withDocument("12345678")
                .withAmount(100000.0)
                .withTerm(12);
    }

    public static ApplicationTestBuilder anApplicationWithLoanTypeAndState() {
        return new ApplicationTestBuilder()
                .withDocument("12345678")
                .withAmount(100000.0)
                .withTerm(12)
                .withLoantypeId(1L)
                .withStateId(1L);
    }

    public static ApplicationTestBuilder anApplicationWithCustomData(String document, double amount, int term) {
        return new ApplicationTestBuilder()
                .withDocument(document)
                .withAmount(amount)
                .withTerm(term);
    }
}