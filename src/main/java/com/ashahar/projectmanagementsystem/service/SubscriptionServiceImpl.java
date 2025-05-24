package com.ashahar.projectmanagementsystem.service;

import com.ashahar.projectmanagementsystem.model.PlanType;
import com.ashahar.projectmanagementsystem.model.Subscription;
import com.ashahar.projectmanagementsystem.model.User;
import com.ashahar.projectmanagementsystem.repo.SubscriptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class SubscriptionServiceImpl implements SubscriptionService{

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionRepo subscriptionRepo;

    @Override
    public Subscription createSubscription(User user) {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setSubscriptionStartDate(LocalDateTime.now());
        subscription.setSubscriptionEndDate(LocalDateTime.now().plusMonths(12)); // Default to 1 month for simplicity
        subscription.setValid(true);
        subscription.setPlantype(PlanType.FREE); // Default plan type

        return subscriptionRepo.save(subscription);
    }

    @Override
    public Subscription getUserSubscription(Long userId) {
        return subscriptionRepo.findByUserId(userId);
    }

    @Override
    public Subscription updateSubscription(Long userId, PlanType planType) {
        return null;
    }

    @Override
    public boolean isValid(Subscription subscription) {
        return false;
    }
}
