package com.example.chat.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.chat.database.message;
@Repository
public interface MessageRepository extends JpaRepository<message, Long> {



    List<message> findByChatRoomIdOrderByTimestampAsc(long roomId);
}