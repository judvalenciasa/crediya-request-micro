package co.com.crediyarequest.model.security;

public class TokenValidationResult {
    private Boolean valid;
    private Long role;
    private String message;

    public TokenValidationResult(Boolean valid, Long role, String message) {
        this.valid = valid;
        this.role = role;
        this.message = message;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
