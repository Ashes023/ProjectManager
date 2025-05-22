package com.ashahar.projectmanagementsystem.service;

import com.ashahar.projectmanagementsystem.model.Invitation;
import org.springframework.stereotype.Service;

@Service
public interface InvitationService {

    public void sendInvitation(String email, Long projectId) throws Exception;

    public Invitation acceptInvitation(String token, Long userId) throws Exception;

    public String getTokenByUserMail(String userEmail) throws Exception;

    void deleteToken(String token) throws Exception;
}
