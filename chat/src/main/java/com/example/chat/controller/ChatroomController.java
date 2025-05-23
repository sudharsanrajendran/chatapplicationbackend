package com.example.chat.controller;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.example.chat.Dto.ChatMessageDto;
import com.example.chat.Dto.ChatRoomRequest;
import com.example.chat.database.ChatRoom;
import com.example.chat.database.User;

import com.example.chat.repository.Userrepository;
import com.example.chat.services.Chatservice;
import com.example.chat.services.MessgaeService;
@RestController
public class ChatroomController {

    @Autowired
    Userrepository userRepo;

    @Autowired
    Chatservice chatservice;

    @Autowired 
    
    MessgaeService messgaeService;

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



@GetMapping("/{roomId}")
public List<ChatMessageDto> getAllMessagesInRoom(@PathVariable Long roomId) {
    return messgaeService.getMessagesByRoomId(roomId);
}


}
