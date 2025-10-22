package dev.jeffery.movie_booking_project_backend.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
