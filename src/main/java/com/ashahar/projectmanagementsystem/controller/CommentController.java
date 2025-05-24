package com.ashahar.projectmanagementsystem.controller;

import com.ashahar.projectmanagementsystem.model.Comment;
import com.ashahar.projectmanagementsystem.request.CreateCommentRequest;
import com.ashahar.projectmanagementsystem.response.MessageResponse;
import com.ashahar.projectmanagementsystem.service.CommentService;
import com.ashahar.projectmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Comment> createComment(
            @RequestBody CreateCommentRequest req,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        Long userId = userService.findUserProfileByJwt(jwt).getId();
        Comment comment = commentService.createComment(req.getIssueId(), userId, req.getContent());
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(
            @PathVariable Long commentId,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        Long userId = userService.findUserProfileByJwt(jwt).getId();
        commentService.deleteComment(commentId, userId);

        MessageResponse messageResponse = new MessageResponse("Comment deleted successfully");
        return new ResponseEntity<>(messageResponse, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comment>> getCommentByIssueId(
            @PathVariable Long issueId
            ) throws Exception {
        List<Comment> comments = commentService.findCommentsByIssueId(issueId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

}
