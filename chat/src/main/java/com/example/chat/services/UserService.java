package com.example.chat.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chat.database.User;
import com.example.chat.repository.Userrepository;

@Service
public class UserService {

    @Autowired 
    private Userrepository userRepository;

    

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


}
