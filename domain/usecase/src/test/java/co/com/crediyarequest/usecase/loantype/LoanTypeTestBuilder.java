package co.com.crediyarequest.usecase.loantype;

import co.com.crediyarequest.model.loantype.LoanType;

public class LoanTypeTestBuilder {

    private Long idloanType = 1L;
    private String name = "Préstamo Personal";
    private double minimumAmount = 10000.0;
    private double maximumAmount = 500000.0;
    private double interestRate = 2.5;
    private Boolean automaticValidation = false;

    public LoanTypeTestBuilder withIdloanType(Long idloanType) {
        this.idloanType = idloanType;
        return this;
    }

    public LoanTypeTestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public LoanTypeTestBuilder withMinimumAmount(double minimumAmount) {
        this.minimumAmount = minimumAmount;
        return this;
    }

    public LoanTypeTestBuilder withMaximumAmount(double maximumAmount) {
        this.maximumAmount = maximumAmount;
        return this;
    }

    public LoanTypeTestBuilder withInterestRate(double interestRate) {
        this.interestRate = interestRate;
        return this;
    }

    public LoanTypeTestBuilder withAutomaticValidation(Boolean automaticValidation) {
        this.automaticValidation = automaticValidation;
        return this;
    }

    public LoanType build() {
        return LoanType.builder()
                .idloanType(idloanType)
                .name(name)
                .minimumAmount(minimumAmount)
                .maximumAmount(maximumAmount)
                .interestRate(interestRate)
                .automaticValidation(automaticValidation)
                .build();
    }

    public static LoanTypeTestBuilder aLoanType() {
        return new LoanTypeTestBuilder();
    }

    public static LoanTypeTestBuilder aPersonalLoan() {
        return new LoanTypeTestBuilder()
                .withIdloanType(1L)
                .withName("Préstamo Personal")
                .withMinimumAmount(10000.0)
                .withMaximumAmount(500000.0)
                .withInterestRate(2.5)
                .withAutomaticValidation(false);
    }

    public static LoanTypeTestBuilder aBusinessLoan() {
        return new LoanTypeTestBuilder()
                .withIdloanType(2L)
                .withName("Préstamo Empresarial")
                .withMinimumAmount(50000.0)
                .withMaximumAmount(1000000.0)
                .withInterestRate(3.0)
                .withAutomaticValidation(true);
    }

    public static LoanTypeTestBuilder aMicrocreditLoan() {
        return new LoanTypeTestBuilder()
                .withIdloanType(3L)
                .withName("Microcrédito")
                .withMinimumAmount(1000.0)
                .withMaximumAmount(10000.0)
                .withInterestRate(4.0)
                .withAutomaticValidation(true);
    }
}
