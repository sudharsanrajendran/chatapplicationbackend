package com.example.chat.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.example.chat.Dto.LoginRequest;
import com.example.chat.Dto.RegisterRequest;
import com.example.chat.services.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;
@PostMapping("/login")
public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
    Map<String, String> response = new HashMap<>();
    try {
        String token = authService.login(request.getEmail(), request.getPassword());

        // ✅ Get the user from AuthService
        Long userId = authService.getUserIdByEmail(request.getEmail());

        response.put("status", "success");
        response.put("message", "Login successful");
        response.put("id", String.valueOf(userId)); // Safely add user ID as string
        response.put("token", token);

        return ResponseEntity.ok(response);
    } catch (BadCredentialsException e) {
        response.put("status", "error");
        response.put("message", "Invalid email or password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    } catch (Exception e) {
        response.put("status", "error");
        response.put("message", "Something went wrong");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

     @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequest request) {
        try {
            String message = authService.register(request);
            return ResponseEntity.ok(Collections.singletonMap("message", message));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("error", ex.getMessage()));
        }
    }
}

