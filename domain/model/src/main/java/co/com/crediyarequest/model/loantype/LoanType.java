package co.com.crediyarequest.model.loantype;

public class LoanType {
    private Long idlongType;
    private String name;
    private double minimumAmount;
    private double maximumAmount;
    private double interestRate;
    private Boolean automaticValidation;

    public LoanType (){
    }

    public LoanType(String name, double minimumAmount, double maximumAmount, double interestRate, Boolean automaticValidation) {
        this.name = name;
        this.minimumAmount = minimumAmount;
        this.maximumAmount = maximumAmount;
        this.interestRate = interestRate;
        this.automaticValidation = automaticValidation;
    }

    public Long getIdlongType() {
        return idlongType;
    }

    public void setIdlongType(Long idlongType) {
        this.idlongType = idlongType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMinimumAmount() {
        return minimumAmount;
    }

    public void setMinimumAmount(double minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    public double getMaximumAmount() {
        return maximumAmount;
    }

    public void setMaximumAmount(double maximumAmount) {
        this.maximumAmount = maximumAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public Boolean getAutomaticValidation() {
        return automaticValidation;
    }

    public void setAutomaticValidation(Boolean automaticValidation) {
        this.automaticValidation = automaticValidation;
    }
}
