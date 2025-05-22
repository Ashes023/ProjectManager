package com.ashahar.projectmanagementsystem.service;

import com.ashahar.projectmanagementsystem.model.Comment;
import com.ashahar.projectmanagementsystem.model.Issue;
import com.ashahar.projectmanagementsystem.model.User;
import com.ashahar.projectmanagementsystem.repo.CommentRepo;
import com.ashahar.projectmanagementsystem.repo.IssueRepo;
import com.ashahar.projectmanagementsystem.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private IssueRepo issueRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public Comment createComment(Long issueId, Long userId, String Comment) {
        Optional<Issue> issueOptional = issueRepo.findById(issueId);
        Optional<User> userOptional = userRepo.findById(userId);

        if(issueOptional.isEmpty())
            throw new RuntimeException("Issue not found on id "+issueId);
        if(userOptional.isEmpty())
            throw new RuntimeException("User not found on id "+userId);

        Issue issue = issueOptional.get();
        User user = userOptional.get();

        Comment comment = new Comment();
        comment.setIssue(issue);
        comment.setUser(user);
        comment.setLocalDateTime(LocalDateTime.now());
        comment.setContent(Comment);

        Comment savedComment = commentRepo.save(comment);
        issue.getComments().add(savedComment);

        return savedComment;
    }

    @Override
    public void deleteComment(Long commentId, Long userId) {
        Optional<Comment> comment = commentRepo.findById(commentId);
        Optional<User> user = userRepo.findById(userId);

        if(comment.isEmpty())
            throw new RuntimeException("Comment not found on id "+commentId);
        if(user.isEmpty())
            throw new RuntimeException("User not found on id "+userId);

        Comment commentToDelete = comment.get();
        User userToDelete = user.get();

        if(commentToDelete.getUser().equals(userToDelete))
            commentRepo.delete(commentToDelete);
        else
            throw new RuntimeException("Do not have permission to delete this comment");

    }

    @Override
    public List<Comment> getAllCommentsByIssueId(Long issueId) {
        return commentRepo.findByIssueId(issueId);
    }
}
