package co.com.crediyarequest.usecase.state;

import co.com.crediyarequest.model.state.State;

public class StateTestBuilder {

    private Long idState = 1L;
    private String name = "Pendiente de revisión";
    private String description = "Solicitud pendiente de revisión por el analista";

    public StateTestBuilder withIdState(Long idState) {
        this.idState = idState;
        return this;
    }

    public StateTestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public StateTestBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public State build() {
        return State.builder()
                .idState(idState)
                .name(name)
                .description(description)
                .build();
    }

    public static StateTestBuilder aState() {
        return new StateTestBuilder();
    }

    public static StateTestBuilder aPendingReviewState() {
        return new StateTestBuilder()
                .withIdState(1L)
                .withName("Pendiente de revisión")
                .withDescription("Solicitud pendiente de revisión por el analista");
    }

    public static StateTestBuilder anApprovedState() {
        return new StateTestBuilder()
                .withIdState(2L)
                .withName("Aprobado")
                .withDescription("Solicitud aprobada por el analista");
    }

    public static StateTestBuilder aRejectedState() {
        return new StateTestBuilder()
                .withIdState(3L)
                .withName("Rechazado")
                .withDescription("Solicitud rechazada por el analista");
    }

    public static StateTestBuilder anInReviewState() {
        return new StateTestBuilder()
                .withIdState(4L)
                .withName("En revisión")
                .withDescription("Solicitud siendo revisada por el analista");
    }
}