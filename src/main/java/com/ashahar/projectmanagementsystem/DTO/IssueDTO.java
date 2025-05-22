package com.ashahar.projectmanagementsystem.DTO;

import com.ashahar.projectmanagementsystem.model.Project;
import com.ashahar.projectmanagementsystem.model.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class IssueDTO {

    private Long id;
    private String title;
    private String description;
    private String status;
    private Long projectID;
    private String priority;
    private LocalDate dueDate;
    private List<String> tags = new ArrayList<>();
    private Project project;

    private User assignee;


}
