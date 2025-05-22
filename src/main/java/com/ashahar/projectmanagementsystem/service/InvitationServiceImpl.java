package com.ashahar.projectmanagementsystem.service;

import com.ashahar.projectmanagementsystem.model.Invitation;
import com.ashahar.projectmanagementsystem.repo.InvitationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService{

    @Autowired
    private InvitationRepo invitationRepo;

    @Autowired
    private EmailService emailService;

    @Override
    public void sendInvitation(String email, Long projectId) throws Exception {

        String InvitationToke = UUID.randomUUID().toString();

        Invitation invitation = new Invitation();
        invitation.setEmail(email);
        invitation.setProjectId(projectId);
        invitation.setToken(InvitationToke);

        invitationRepo.save(invitation);

        String invitationLink = "http://localhost:5173/accept_invitation?token=" + InvitationToke;
        emailService.sendEmailWithToken(email, invitationLink);

    }

    @Override
    public Invitation acceptInvitation(String token, Long userId) throws Exception {
        Invitation invitation = invitationRepo.findByToken(token);
        if(invitation == null){
            throw new Exception("Invalid Invitation");
        }

        return invitation;
    }

    @Override
    public String getTokenByUserMail(String userEmail) throws Exception {
        return "";
    }

    @Override
    public void deleteToken(String token) throws Exception {

        Invitation invitation = invitationRepo.findByToken(token);
        invitationRepo.delete(invitation);

    }
}
