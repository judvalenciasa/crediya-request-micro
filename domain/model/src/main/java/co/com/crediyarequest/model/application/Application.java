package co.com.crediyarequest.model.application;
//import lombok.NoArgsConstructor;


public class Application {
    private Long id;
    private Long idState;
    private Long longTypeId;
    private double amount;
    private int term;
    private String document;

    public Application() {
    }

    public Application(Long idState, Long longTypeId, double amount, int term, String document) {

        this.idState = idState;
        this.longTypeId = longTypeId;
        this.amount = amount;
        this.term = term;
        this.document = document;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdState() {
        return idState;
    }

    public void setIdState(Long idState) {
        this.idState = idState;
    }

    public Long getLongTypeId() {
        return longTypeId;
    }

    public void setLongTypeId(Long longTypeId) {
        this.longTypeId = longTypeId;
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
