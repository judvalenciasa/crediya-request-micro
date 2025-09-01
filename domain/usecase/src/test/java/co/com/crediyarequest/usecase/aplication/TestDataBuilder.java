package co.com.crediyarequest.usecase.aplication;
import co.com.crediyarequest.model.application.Application;
import co.com.crediyarequest.model.loantype.LoanType;
import co.com.crediyarequest.model.state.State;
import co.com.crediyarequest.usecase.loantype.LoanTypeTestBuilder;
import co.com.crediyarequest.usecase.state.StateTestBuilder;

public class TestDataBuilder {

    public static Application createDefaultApplication() {
        return ApplicationTestBuilder.anApplicationWithDefaults().build();
    }

    public static Application createApplicationWithLoanTypeAndState() {
        return ApplicationTestBuilder.anApplicationWithLoanTypeAndState().build();
    }

    public static Application createApplicationWithCustomData(String document, double amount, int term) {
        return ApplicationTestBuilder.anApplicationWithCustomData(document, amount, term).build();
    }

    public static Application createApplicationWithCustomData(String document, double amount) {
        return ApplicationTestBuilder.anApplicationWithCustomData(document, amount, 12).build();
    }

    public static Application createApplicationWithId(Long idRequest) {
        return ApplicationTestBuilder.anApplicationWithDefaults()
                .withIdRequest(idRequest)
                .build();
    }

    public static LoanType createDefaultLoanType() {
        return LoanTypeTestBuilder.aPersonalLoan().build();
    }

    public static LoanType createBusinessLoanType() {
        return LoanTypeTestBuilder.aBusinessLoan().build();
    }

    public static LoanType createMicrocreditLoanType() {
        return LoanTypeTestBuilder.aMicrocreditLoan().build();
    }

    public static LoanType createLoanTypeForAmount(double amount) {
        if (amount >= 1000 && amount <= 10000) {
            return LoanTypeTestBuilder.aMicrocreditLoan().build();
        } else if (amount >= 10000 && amount <= 500000) {
            return LoanTypeTestBuilder.aPersonalLoan().build();
        } else if (amount >= 50000 && amount <= 1000000) {
            return LoanTypeTestBuilder.aBusinessLoan().build();
        } else {
            return LoanTypeTestBuilder.aPersonalLoan().build();
        }
    }

    public static State createPendingReviewState() {
        return StateTestBuilder.aPendingReviewState().build();
    }

    public static State createApprovedState() {
        return StateTestBuilder.anApprovedState().build();
    }

    public static State createRejectedState() {
        return StateTestBuilder.aRejectedState().build();
    }

    public static State createInReviewState() {
        return StateTestBuilder.anInReviewState().build();
    }
}
