package dev.jeffery.movie_booking_project_backend.controllers;

import dev.jeffery.movie_booking_project_backend.services.AdminService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AdminLoginRequest request) {
        try {
            boolean authenticated = adminService.authenticateUser(request.getUserID(), request.getPassword());
            if (authenticated) {
                return ResponseEntity.ok("Admin login successful");
            } else {
                return ResponseEntity.status(401).body("Invalid admin credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @Data
    @AllArgsConstructor
    static class AdminLoginRequest {
        private String userID;
        private String password;
    }
}
