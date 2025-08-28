package co.com.crediyarequest.model.application;
//import lombok.NoArgsConstructor;


public class Application {
    private Long idRequest;
    private Long idState;
    private Long idloanType;
    private double amount;
    private int term;
    private String document;

    public Application() {
    }

    public Application(Long idState, Long longtypeId, double amount, int term, String document) {
        this.idState = idState;
        this.idloanType = longtypeId;
        this.amount = amount;
        this.term = term;
        this.document = document;
    }

    public Application(Long idloanType, double amount, int term, String document) {
        this.idloanType = idloanType;
        this.amount = amount;
        this.term = term;
        this.document = document;
    }

    public Long getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(Long idRequest) {
        this.idRequest = idRequest;
    }

    public Long getIdState() {
        return idState;
    }

    public void setIdState(Long idState) {
        this.idState = idState;
    }

    public Long getIdloanType() {
        return idloanType;
    }

    public void setIdloanType(Long idloanType) {
        this.idloanType = idloanType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }
    public String getDocument() {
        return document;
    }
    public void setDocument(String document) {
        this.document = document;
    }
}
