package com.example.chat.services;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chat.Dto.ChatMessageDto;
import com.example.chat.database.message;
import com.example.chat.repository.MessageRepository;

@Service
public class MessgaeService {

    @Autowired 
    MessageRepository messageRepository;

public List<ChatMessageDto> getMessagesByRoomId(Long roomId) {
    List<message> messages = messageRepository.findByChatRoomIdOrderByTimestampAsc(roomId);
    return messages.stream()
        .map(m -> new ChatMessageDto(
            m.getSender().getId(),
            m.getReceiver().getId(),
            m.getMessageContent()
        ))
        .collect(Collectors.toList());
}



    

 

}
