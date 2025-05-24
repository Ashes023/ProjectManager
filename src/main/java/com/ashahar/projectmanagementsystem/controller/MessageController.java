package com.ashahar.projectmanagementsystem.controller;

import com.ashahar.projectmanagementsystem.model.Chat;
import com.ashahar.projectmanagementsystem.model.Message;
import com.ashahar.projectmanagementsystem.model.User;
import com.ashahar.projectmanagementsystem.request.CreateMessageRequest;
import com.ashahar.projectmanagementsystem.service.MessageService;
import com.ashahar.projectmanagementsystem.service.ProjectService;
import com.ashahar.projectmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    // Define endpoints for sending and retrieving messages here
    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody CreateMessageRequest request) throws Exception {

        User user = userService.findUserById(request.getSenderId());

        Chat chats = projectService.getProjectById(request.getProjectId()).getChat();
        if(chats == null) throw new Exception("Chat not found for project");

        Message message = messageService.sendMessage(request.getSenderId(), request.getProjectId(), request.getContent());
        return new ResponseEntity<>(message, HttpStatus.CREATED);

    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessagesByProjectId(@PathVariable Long projectId) throws Exception {

        List<Message> messages = messageService.getMessagesByProjectId(projectId);
        return new ResponseEntity<>(messages, HttpStatus.OK);

    }

}
