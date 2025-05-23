package com.example.chat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.chat.database.User;
@Repository
public interface Userrepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
     List<User> findByEmailNot(String email);
     List<User>findByUsernameContainingIgnoreCase(String username);


}
