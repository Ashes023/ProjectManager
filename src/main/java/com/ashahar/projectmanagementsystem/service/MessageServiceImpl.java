package com.ashahar.projectmanagementsystem.service;

import com.ashahar.projectmanagementsystem.model.Chat;
import com.ashahar.projectmanagementsystem.model.Message;
import com.ashahar.projectmanagementsystem.model.User;
import com.ashahar.projectmanagementsystem.repo.MessageRepo;
import com.ashahar.projectmanagementsystem.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProjectService projectService;

    @Override
    public Message sendMessage(Long senderId, Long projectId, String content) throws Exception {
        User sender = userRepo.findById(senderId).orElseThrow(() -> new Exception("User not found"));

        Chat chat = projectService.getProjectById(projectId).getChat();

        Message message = new Message();
        message.setSender(sender);
        message.setChat(chat);
        message.setContent(content);
        message.setCreatedAt(LocalDateTime.now());

        Message saved = messageRepo.save(message);

        chat.getMessages().add(saved);
        return saved;
    }

    @Override
    public List<Message> getMessagesByProjectId(Long projectId) throws Exception {
        Chat chat = projectService.getChatByProjectId(projectId);
        return messageRepo.findByChatIdOrderByCreatedAtAsc(chat.getId());
    }
}
