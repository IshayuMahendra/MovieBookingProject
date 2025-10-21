package dev.jeffery.movie_booking_project_backend.services;

import dev.jeffery.movie_booking_project_backend.data.Admin;
import dev.jeffery.movie_booking_project_backend.repositories.AdminRepository;
import dev.jeffery.movie_booking_project_backend.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Admin createNewAdmin(String userID, String password) {

        Admin admin = new Admin(userID, password);
        adminRepository.save(admin);

        return admin;
    }

    public boolean authenticateUser(String userID, String rawPassword) {
        Admin admin = adminRepository.findByUserID(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return passwordEncoder.matches(rawPassword, admin.getPassword());
    }
}
