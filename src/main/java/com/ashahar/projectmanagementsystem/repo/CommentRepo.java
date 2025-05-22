package com.ashahar.projectmanagementsystem.repo;

import com.ashahar.projectmanagementsystem.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {

    List<Comment> findByIssueId(Long issueId);

}
