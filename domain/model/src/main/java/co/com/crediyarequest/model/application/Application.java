package co.com.crediyarequest.model.application;


import lombok.Builder;

@Builder
public class Application {
    private Long idRequest;
    private Long stateId;
    private Long loantypeId;
    private double amount;
    private int term;
    private String document;


    public Long getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(Long idRequest) {
        this.idRequest = idRequest;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getLoantypeId() {
        return loantypeId;
    }

    public void setLoantypeId(Long loantypeId) {
        this.loantypeId = loantypeId;
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
