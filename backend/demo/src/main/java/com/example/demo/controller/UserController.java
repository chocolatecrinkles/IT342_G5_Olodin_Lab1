package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import com.example.demo.service.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.model.User;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private AuthService authService;

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        System.out.println(">>> /api/user/me HIT <<<");
        System.out.println("Auth object = " + authentication);
        String email = authentication.getName();

        User user = authService.getCurrentUser(email);

        return ResponseEntity.ok(new UserResponse(
                user.getUserId(),
                user.getEmail(),
                user.getFirstname(),
                user.getLastname(),
                user.getCreatedAt()
        ));
    }

    record UserResponse(
            Long userId,
            String email,
            String firstname,
            String lastname,
            Object createdAt
    ) {}
}
    