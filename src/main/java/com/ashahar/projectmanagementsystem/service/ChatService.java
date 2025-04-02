package com.ashahar.projectmanagementsystem.service;

import com.ashahar.projectmanagementsystem.model.Chat;
import org.springframework.stereotype.Service;

@Service
public interface ChatService {

    Chat createChat(Chat chat);
}
