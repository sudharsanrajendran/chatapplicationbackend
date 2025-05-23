package com.example.chat.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.chat.database.ChatRoom;
import com.example.chat.database.User;
@Repository
public interface ChatrRoomREPO extends JpaRepository<ChatRoom,Long>{
Optional<ChatRoom> findBySenderAndReceiver(User sender, User receiver);


}
