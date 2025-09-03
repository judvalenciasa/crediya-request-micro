package co.com.crediyarequest.model.state;

import lombok.Builder;

@Builder
public class State {
    private Long idState;
    private String name;
    private String description;

    public State() {
    }

    public State(Long idState, String name, String description) {
        this.idState = idState;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Long getIdState() {
        return idState;
    }

    public void setIdState(Long idState) {
        this.idState = idState;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
