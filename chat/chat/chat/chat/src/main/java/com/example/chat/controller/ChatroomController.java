package com.example.chat.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.chat.Dto.ChatRoomRequest;
import com.example.chat.database.ChatRoom;
import com.example.chat.database.User;
import com.example.chat.repository.Userrepository;
import com.example.chat.services.Chatservice;
@RestController
public class ChatroomController {

    @Autowired
    Userrepository userRepo;

    @Autowired
    Chatservice chatservice;

    @Autowired
    Userrepository userrepository;

    @PostMapping("/chatroom")
    public ResponseEntity<?> getOrCreateChatRoom(@RequestBody ChatRoomRequest request) {
    User sender = userrepository.findById(request.getSenderId())
    .orElseThrow(() -> new RuntimeException("Sender not found"));

User receiver = userrepository.findById(request.getReceiverId())
    .orElseThrow(() -> new RuntimeException("Receiver not found"));

        ChatRoom chatRoom = chatservice.getOrCreateChatRoom(sender.getId(), receiver.getId());

        return ResponseEntity.ok(Map.of("Id", chatRoom.getId()));
    }
}
