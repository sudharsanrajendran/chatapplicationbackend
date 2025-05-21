package com.example.chat.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chat.Dto.ChatMessageDto;
import com.example.chat.database.ChatRoom;
import com.example.chat.database.User;
import com.example.chat.database.message;
import com.example.chat.repository.ChatrRoomREPO;
import com.example.chat.repository.MessageRepository;
import com.example.chat.repository.Userrepository;

@Service
public class Chatservice {

    @Autowired
    private ChatrRoomREPO chatRoomRepo;

    @Autowired
    private MessageRepository messageRepo;

    @Autowired
    private Userrepository userRepo;

    public void saveMessage(ChatMessageDto chatMessage) {
        User sender = userRepo.findById(chatMessage.getSenderId()).orElseThrow(() ->
                new RuntimeException("Sender not found"));
        User receiver = userRepo.findById(chatMessage.getReceiverId()).orElseThrow(() ->
                new RuntimeException("Receiver not found"));

        // 🔍 Try to find existing chat room between sender & receiver (in any order)
        Optional<ChatRoom> existingRoom = findChatRoomBetweenUsers(sender, receiver);

        ChatRoom chatRoom = existingRoom.orElseGet(() -> {
            ChatRoom newRoom = new ChatRoom();
            newRoom.setSender(sender);
            newRoom.setReceiver(receiver);
            ChatRoom savedRoom = chatRoomRepo.save(newRoom);
            System.out.println("🆕 ChatRoom created with ID: " + savedRoom.getId());
            return savedRoom;
        });

        // 💬 Save the message under that chat room
        message msg = new message();
        msg.setChatRoom(chatRoom);
        msg.setSender(sender);
        msg.setReceiver(receiver);
        msg.setMessageContent(chatMessage.getContent());
        msg.setTimestamp(LocalDateTime.now());

        message savedMessage = messageRepo.save(msg);

        // ✅ Log output
        System.out.println("📤 Message sent successfully!");
        System.out.println("   ➤ ChatRoom ID: " + chatRoom.getId());
        System.out.println("   ➤ From: " + sender.getEmail());
        System.out.println("   ➤ To: " + receiver.getEmail());
        System.out.println("   ➤ Message: " + savedMessage.getMessageContent());
        System.out.println("   ➤ Timestamp: " + savedMessage.getTimestamp());
    }

    private Optional<ChatRoom> findChatRoomBetweenUsers(User user1, User user2) {
        Optional<ChatRoom> room = chatRoomRepo.findBySenderAndReceiver(user1, user2);
        if (room.isPresent()) {
            System.out.println("✅ Found existing room (user1 ➝ user2)");
            return room;
        }
        room = chatRoomRepo.findBySenderAndReceiver(user2, user1);
        if (room.isPresent()) {
            System.out.println("✅ Found existing room (user2 ➝ user1)");
        }
        return room;
    }

    public List<message> getMessagesByRoomId(long roomId) {
        List<message> messages = messageRepo.findByChatRoomIdOrderByTimestampAsc(roomId);
        System.out.println("📥 Fetched " + messages.size() + " messages from roomId: " + roomId);
        return messages;
    }

    public ChatRoom getOrCreateChatRoom(Long senderId, Long receiverId) {
        User sender = userRepo.findById(senderId).orElseThrow();
        User receiver = userRepo.findById(receiverId).orElseThrow();

        Optional<ChatRoom> existingRoom = findChatRoomBetweenUsers(sender, receiver);
        if (existingRoom.isPresent()) {
            System.out.println("🔁 Returning existing ChatRoom with ID: " + existingRoom.get().getId());
            return existingRoom.get();
        }

        ChatRoom newRoom = new ChatRoom();
        newRoom.setSender(sender);
        newRoom.setReceiver(receiver);
        ChatRoom savedRoom = chatRoomRepo.save(newRoom);
        System.out.println("🆕 2 New ChatRoom created with ID: " + savedRoom.getId());
        return savedRoom;
    }
}
