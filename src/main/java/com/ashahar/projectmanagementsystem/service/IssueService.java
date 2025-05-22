package com.ashahar.projectmanagementsystem.service;

import com.ashahar.projectmanagementsystem.model.Issue;
import com.ashahar.projectmanagementsystem.model.User;
import com.ashahar.projectmanagementsystem.request.IssueRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IssueService {

    Issue getIssueById(Long issueId) throws Exception;

    List<Issue> getIssueByProjectId(Long projectId);

    Issue createIssue(IssueRequest issue, User user) throws Exception;

    void deleteIssue(Long issueId, Long userId) throws Exception;

    Issue addUserToIssue(Long issueId, Long userId) throws Exception;

    Issue updateStatus(Long issueId, String status) throws Exception;
}
