package com.example.chat.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.chat.database.ChatRequest;
import com.example.chat.database.User;
import com.example.chat.repository.ChatRequestRepository;
import com.example.chat.repository.Userrepository;


@RestController
public class ChatrequestController {

    @Autowired private ChatRequestRepository repo;
    @Autowired private Userrepository userRepo;

    @PostMapping("/send")
    public ResponseEntity<?> sendRequest(@RequestParam Long senderId, @RequestParam Long receiverId) {
        User sender = userRepo.findById(senderId).orElseThrow();
        User receiver = userRepo.findById(receiverId).orElseThrow();
        
        if (repo.findBySenderAndReceiver(sender, receiver).isPresent()) {
            return ResponseEntity.badRequest().body("Request already sent");
        }

        ChatRequest request = new ChatRequest();
        request.setSender(sender);
        request.setReceiver(receiver);
        request.setStatus(ChatRequest.Status.PENDING);
        return ResponseEntity.ok(repo.save(request));
    }

    @PostMapping("/respond")
    public ResponseEntity<?> respondRequest(@RequestParam Long requestId, @RequestParam String decision) {
        ChatRequest request = repo.findById(requestId).orElseThrow();
        request.setStatus(ChatRequest.Status.valueOf(decision.toUpperCase()));
        return ResponseEntity.ok(repo.save(request));
    }

    @GetMapping("/status")
    public ResponseEntity<?> getStatus(@RequestParam Long senderId, @RequestParam Long receiverId) {
 User sender = userRepo.findById(senderId).orElseThrow(() -> new RuntimeException("Sender not found"));
User receiver = userRepo.findById(receiverId).orElseThrow(() -> new RuntimeException("Receiver not found"));

Optional<ChatRequest> optionalRequest = repo.findBySenderAndReceiver(sender, receiver);
ChatRequest.Status status = optionalRequest.map(ChatRequest::getStatus).orElse(ChatRequest.Status.PENDING);

return ResponseEntity.ok(status);

    }

    @GetMapping("/pending")
    public ResponseEntity<?> getPendingRequests(@RequestParam Long receiverId) {
        User receiver = userRepo.findById(receiverId).orElseThrow();
        return ResponseEntity.ok(repo.findByReceiverAndStatus(receiver, ChatRequest.Status.PENDING));
    }
}
