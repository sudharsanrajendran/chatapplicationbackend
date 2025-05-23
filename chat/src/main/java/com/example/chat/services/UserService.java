package com.example.chat.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.chat.database.User;
import com.example.chat.repository.Userrepository;
import com.example.chat.utils.JwtUtil;

@Service
public class UserService {

    @Autowired 
    private Userrepository userRepository;

      @Autowired
    private JwtUtil jwtUtil;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getAllUsersExceptLoggedIn(String token) {
        String email = jwtUtil.extractUsername(token);
        return userRepository.findByEmailNot(email);
    }
  

public List<User> searchUsers(String query) {
        String cleanedQuery = query.startsWith("@") ? query.substring(1) : query;
        return userRepository.findByUsernameContainingIgnoreCase(cleanedQuery);
    }
      

    public User updateUserDetails(Long userId, String newUsername, String newAbout) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

    if (newUsername != null && !newUsername.trim().isEmpty()) {
        user.setUsername(newUsername);
    }

    if (newAbout != null && !newAbout.trim().isEmpty()) {
        user.setAbout(newAbout);
    }

    return userRepository.save(user);
}

}
