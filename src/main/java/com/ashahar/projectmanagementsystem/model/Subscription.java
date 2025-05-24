package com.ashahar.projectmanagementsystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime subscriptionStartDate;
    private LocalDateTime subscriptionEndDate;

    private PlanType plantype;

    private boolean isValid;

    @OneToOne
    private User user;
}
