package com.ashahar.projectmanagementsystem.service;

import com.ashahar.projectmanagementsystem.model.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    Comment createComment(Long issueId, Long userId, String Comment);

    void deleteComment(Long commentId, Long userId);

    List<Comment> getAllCommentsByIssueId(Long issueId);

}
