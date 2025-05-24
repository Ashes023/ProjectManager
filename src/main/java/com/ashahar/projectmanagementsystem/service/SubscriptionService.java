package com.ashahar.projectmanagementsystem.service;

import com.ashahar.projectmanagementsystem.model.PlanType;
import com.ashahar.projectmanagementsystem.model.Subscription;
import com.ashahar.projectmanagementsystem.model.User;
import org.springframework.stereotype.Service;

@Service
public interface SubscriptionService {

    Subscription createSubscription(User user);

    Subscription getUserSubscription(Long userId);

    Subscription updateSubscription(Long userId, PlanType planType);

    boolean isValid(Subscription subscription);
}
