package com.example.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.chat.database.ChatRequest;
import com.example.chat.database.User;

public interface ChatRequestRepository extends JpaRepository<ChatRequest, Long> {
    java.util.Optional<ChatRequest> findBySenderAndReceiver(User sender, User receiver);
    List<ChatRequest> findByReceiverAndStatus(User receiver, ChatRequest.Status status);
}
