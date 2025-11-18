package dev.jeffery.movie_booking_project_backend.services;

import dev.jeffery.movie_booking_project_backend.data.Admin;
import dev.jeffery.movie_booking_project_backend.data.Promotion;
import dev.jeffery.movie_booking_project_backend.data.User;
import dev.jeffery.movie_booking_project_backend.repositories.AdminRepository;
import dev.jeffery.movie_booking_project_backend.repositories.PromotionRepository;
import dev.jeffery.movie_booking_project_backend.repositories.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private EmailService emailService;

    public Admin createNewAdmin(String userID, String password) {

        Admin admin = new Admin(userID, passwordEncoder.encode(password));
        adminRepository.save(admin);

        return admin;
    }

    public boolean authenticateUser(String userID, String rawPassword) {
        Admin admin = adminRepository.findByUserID(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return passwordEncoder.matches(rawPassword, admin.getPassword());
    }

    // add promotions and send email to everyone opted in
    public Promotion addPromotion(Promotion promotion){
        promotionRepository.save(promotion);
        List<User> users = userRepository.findUserByPromotions(true);
        for(User u : users){
            emailService.sendPromotionEmail(u.getEmail(), promotion);
        }
        return promotion;
    }
}
