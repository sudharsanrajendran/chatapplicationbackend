package com.example.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.chat.database.User;
import com.example.chat.services.UserService;

@RestController
public class UserController {

    @Autowired private UserService userService;
    @GetMapping("/getall")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsersExceptCurrent(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7); // remove "Bearer "
        List<User> users = userService.getAllUsersExceptLoggedIn(token);
        return ResponseEntity.ok(users);
    }
      @GetMapping("/search")
public ResponseEntity<List<User>> searchUsers(@RequestParam String query) {
    // Remove '@' if present
    String cleanedQuery = query.startsWith("@") ? query.substring(1) : query;

    List<User> users = userService.searchUsers(cleanedQuery);
    return ResponseEntity.ok(users);
}
    
@PutMapping("/{id}/update")
public User updateUserDetails(
        @PathVariable Long id,
        @RequestParam(required = false) String newUsername,
        @RequestParam(required = false) String newAbout) {
    return userService.updateUserDetails(id, newUsername, newAbout);
}


}
