package com.ashahar.projectmanagementsystem.service;

import com.ashahar.projectmanagementsystem.model.PlanType;
import com.ashahar.projectmanagementsystem.model.Subscription;
import com.ashahar.projectmanagementsystem.model.User;
import com.ashahar.projectmanagementsystem.repo.SubscriptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
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
        Subscription subscription = subscriptionRepo.findByUserId(userId);
        if(!isValid(subscription)) {
            subscription.setPlantype(PlanType.FREE);
            subscription.setSubscriptionStartDate(LocalDateTime.now());
            subscription.setSubscriptionEndDate(LocalDateTime.now().plusMonths(12));
        }
        return subscriptionRepo.save(subscription);
    }

    @Override
    public Subscription updateSubscription(Long userId, PlanType planType) {
        Subscription subscription = subscriptionRepo.findByUserId(userId);
        subscription.setPlantype(planType);
        subscription.setSubscriptionStartDate(LocalDateTime.now());
        if (planType == PlanType.MONTHLY) {
            subscription.setSubscriptionEndDate(LocalDateTime.now().plusMonths(1));
        } else if (planType == PlanType.ANNUALLY) {
            subscription.setSubscriptionEndDate(LocalDateTime.now().plusYears(1));
        } else {
            subscription.setSubscriptionEndDate(LocalDateTime.now().plusMonths(12)); // Default to 1 year
        }

        return subscriptionRepo.save(subscription);
    }

    @Override
    public boolean isValid(Subscription subscription) {
        if(subscription.getPlantype() == PlanType.FREE) {
            return true; // Free plan is always valid
        }

        LocalDateTime endDate = subscription.getSubscriptionEndDate();
        LocalDateTime now = LocalDateTime.now();

        return endDate.isAfter(now) || endDate.isEqual(now);
    }
}
