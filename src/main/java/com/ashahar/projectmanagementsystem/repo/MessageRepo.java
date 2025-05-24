package com.ashahar.projectmanagementsystem.repo;

import com.ashahar.projectmanagementsystem.model.Chat;
import com.ashahar.projectmanagementsystem.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {

    List<Message> findByChatIdOrderByCreatedAtAsc(Long chat_id);
    // Custom query methods can be defined here if needed
    // For example, to find messages by project ID:
    // List<Message> findByProjectId(Long projectId);
}
