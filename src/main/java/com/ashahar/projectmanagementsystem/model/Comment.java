package com.ashahar.projectmanagementsystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;
    private LocalDateTime localDateTime;

    @ManyToOne
    private User user;

    @ManyToOne
    private Issue issue;
}
