package com.ashahar.projectmanagementsystem.controller;

import com.ashahar.projectmanagementsystem.DTO.IssueDTO;
import com.ashahar.projectmanagementsystem.model.Issue;
import com.ashahar.projectmanagementsystem.model.User;
import com.ashahar.projectmanagementsystem.request.IssueRequest;
import com.ashahar.projectmanagementsystem.response.AuthResponse;
import com.ashahar.projectmanagementsystem.response.MessageResponse;
import com.ashahar.projectmanagementsystem.service.IssueService;
import com.ashahar.projectmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;

    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long issueId) throws Exception {

        return ResponseEntity.ok(issueService.getIssueById(issueId));

    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssuesByProjectId(@PathVariable Long projectId) throws Exception{
            return ResponseEntity.ok(issueService.getIssueByProjectId(projectId));
    }

    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueRequest issue,
                                                @RequestHeader("Authorization") String token) throws Exception {

        User tokenUser = userService.findUserProfileByJwt(token);
        User user = userService.findUserById(tokenUser.getId());

        Issue createdIssue = issueService.createIssue(issue, tokenUser);
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setId(createdIssue.getId());
        issueDTO.setTitle(createdIssue.getTitle());
        issueDTO.setDescription(createdIssue.getDescription());
        issueDTO.setStatus(createdIssue.getStatus());
        issueDTO.setProjectID(createdIssue.getProjectID());
        issueDTO.setPriority(createdIssue.getPriority());
        issueDTO.setDueDate(createdIssue.getDueDate());
        issueDTO.setTags(createdIssue.getTags());
        issueDTO.setProject(createdIssue.getProject());
        issueDTO.setAssignee(createdIssue.getAssignee());

        return ResponseEntity.ok(issueDTO);
    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<MessageResponse> deleteIssue(@PathVariable Long issueId,
                                                    @RequestHeader("Authorization") String token) throws Exception {

        User tokenUser = userService.findUserProfileByJwt(token);
        issueService.deleteIssue(issueId, tokenUser.getId());

        MessageResponse response = new MessageResponse();
        response.setMessage("Issue deleted successfully");

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<Issue> addUserToIssue(@PathVariable Long issueId,
                                             @PathVariable Long userId) throws Exception {

        Issue updatedIssue = issueService.addUserToIssue(issueId, userId);
        return ResponseEntity.ok(updatedIssue);

    }

    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issue> updateIssueStatus(@PathVariable Long issueId,
                                              @PathVariable String status) throws Exception {

        Issue updatedIssue = issueService.updateStatus(issueId, status);
        return ResponseEntity.ok(updatedIssue);

    }
}
