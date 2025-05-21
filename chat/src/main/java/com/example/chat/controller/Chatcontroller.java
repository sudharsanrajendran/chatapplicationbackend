package com.example.chat.controller;





import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import org.springframework.web.bind.annotation.RestController;

import com.example.chat.Dto.ChatMessageDto;

import com.example.chat.services.Chatservice;
@RestController
public class Chatcontroller {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    Chatservice chatservice;

  @MessageMapping("/chat/{roomId}")
public void processMessage(@DestinationVariable Long roomId, ChatMessageDto msg) {
    try {
        chatservice.saveMessage(msg);
      messagingTemplate.convertAndSend("/topic/room/" + roomId, msg);

        System.out.println("📩 Data saved successfully:");
        System.out.println("   ➤ RoomId: " + roomId);
        System.out.println("   ➤ Message: " + msg);
    } catch (Exception e) {
        System.err.println("❌ Error in WebSocket message handler: " + e.getMessage());
        e.printStackTrace();
    }
}

}







