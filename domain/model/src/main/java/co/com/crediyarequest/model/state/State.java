package co.com.crediyarequest.model.state;

public class State {
    private String name;
    private double montoMinimo;
    private double montoMaximo;
    private double tasaInteres;
    private Boolean automaticValidation;

    public State (){

    }

    public State(String name, double montoMinimo, double montoMaximo, double tasaInteres, Boolean automaticValidation) {
        this.name = name;
        this.montoMinimo = montoMinimo;
        this.montoMaximo = montoMaximo;
        this.tasaInteres = tasaInteres;
        this.automaticValidation = automaticValidation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMontoMinimo() {
        return montoMinimo;
    }

    public void setMontoMinimo(double montoMinimo) {
        this.montoMinimo = montoMinimo;
    }

    public double getMontoMaximo() {
        return montoMaximo;
    }

    public void setMontoMaximo(double montoMaximo) {
        this.montoMaximo = montoMaximo;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(double tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public Boolean getAutomaticValidation() {
        return automaticValidation;
    }

    public void setAutomaticValidation(Boolean automaticValidation) {
        this.automaticValidation = automaticValidation;
    }
}
