package dev.jeffery.movie_booking_project_backend.dto;

public class ResetPasswordRequest {
    private String token;
    private String newPassword;
    private String confirm;

    public ResetPasswordRequest() {}

    public ResetPasswordRequest(String token, String newPassword, String confirm) {
        this.token = token;
        this.newPassword = newPassword;
        this.confirm = confirm;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }

    public String getConfirm() { return confirm; }
    public void setConfirm(String confirm) { this.confirm = confirm; }
}
