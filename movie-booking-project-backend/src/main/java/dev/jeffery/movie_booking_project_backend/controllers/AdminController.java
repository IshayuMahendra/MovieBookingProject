package dev.jeffery.movie_booking_project_backend.controllers;

import dev.jeffery.movie_booking_project_backend.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AdminController {
    @Autowired
    private AdminService adminService;


}