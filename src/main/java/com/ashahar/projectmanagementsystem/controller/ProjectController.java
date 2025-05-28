package com.ashahar.projectmanagementsystem.controller;

import com.ashahar.projectmanagementsystem.model.Chat;
import com.ashahar.projectmanagementsystem.model.Invitation;
import com.ashahar.projectmanagementsystem.model.Project;
import com.ashahar.projectmanagementsystem.model.User;
import com.ashahar.projectmanagementsystem.request.InviteRequest;
import com.ashahar.projectmanagementsystem.response.MessageResponse;
import com.ashahar.projectmanagementsystem.service.InvitationService;
import com.ashahar.projectmanagementsystem.service.ProjectService;
import com.ashahar.projectmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private InvitationService invitationService;

    @GetMapping
    public ResponseEntity<List<Project>> getProjects(
            @RequestParam(required=false) String category,
            @RequestParam(required=false) String tag,
            @RequestParam("Authorization") String jwt) throws Exception {


        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.getProjectsByTeam(user, category, tag);

        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(
            @PathVariable Long projectId,
            @RequestParam("Authorization") String jwt) throws Exception {


        User user = userService.findUserProfileByJwt(jwt);
        Project project = projectService.getProjectById(projectId);

        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(
//            @PathVariable Long projectId,
            @RequestParam("Authorization") String jwt,
            @RequestBody Project project) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        Project createProject = projectService.createProject(project, user);

        return new ResponseEntity<>(createProject, HttpStatus.OK);
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long projectId,
            @RequestParam("Authorization") String jwt,
            @RequestBody Project project) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        Project updatedProject = projectService.updateProject(project, projectId);

        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponse> deleteProject(
            @PathVariable Long projectId,
            @RequestParam("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        projectService.deleteProject(projectId, user.getId());
        MessageResponse messageResponse = new MessageResponse("Project deleted successfully");

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProject(
            @RequestParam(required=false) String keyword,
//            @RequestParam(required=false) String tag,
            @RequestParam("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.searchProjects(keyword, user);

        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectId}/chat")
    public ResponseEntity<Chat> getChatByProjectId(
            @PathVariable Long projectId,
            @RequestParam("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        Chat chat = projectService.getChatByProjectId(projectId);

        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @PostMapping("/invite")
    public ResponseEntity<MessageResponse> inviteProject(
            @RequestParam InviteRequest req,
            @RequestParam("Authorization") String jwt,
            @RequestBody Project project) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        invitationService.sendInvitation(req.getEmail(), req.getProjectId());

        MessageResponse res = new MessageResponse("Invitation sent successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/accept_invitation ")
    public ResponseEntity<Invitation> acceptInviteProject(
            @RequestParam String token,
            @RequestParam("Authorization") String jwt,
            @RequestBody Project project) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        Invitation invitation = invitationService.acceptInvitation(token, user.getId());
        projectService.addUserToProject(invitation.getProjectId(), user.getId());

//        MessageResponse res = new MessageResponse("Invitation sent successfully");
        return new ResponseEntity<>(invitation, HttpStatus.ACCEPTED);
    }
}

