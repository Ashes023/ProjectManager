package com.ashahar.projectmanagementsystem.service;

import com.ashahar.projectmanagementsystem.model.Chat;
import com.ashahar.projectmanagementsystem.repo.ChatRepo;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService{

    private ChatRepo chatRepo;

    @Override
    public Chat createChat(Chat chat) {
        return chatRepo.save(chat);
    }
}
